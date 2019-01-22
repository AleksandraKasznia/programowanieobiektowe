package lab1zaddom;
import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PooledConnectionHandler implements Runnable {
    protected Socket connection;
    protected static List pool = new LinkedList();
    OutputStream outputStream;
    InputStream inputStream;
    Queue<Integer> serversQueue;
    ExecutorService esSplitData = Executors.newCachedThreadPool();

    public PooledConnectionHandler(final Queue<Integer> serversQueue) {
        this.serversQueue = serversQueue;
    }

    public void handleConnection() {
        try {
            outputStream = connection.getOutputStream();
            inputStream = connection.getInputStream();
            PrintWriter streamWriter = new PrintWriter(outputStream,true);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine = streamReader.readLine();
            String port;
            try {
                port = inputLine.substring(0, 4);
            }
            catch( IndexOutOfBoundsException e){
                port = "not a server";
            }
            if (port.equals("port")){
                connectingServer(inputLine, streamReader, streamWriter);
            }
            else{
                connectingClient(inputLine);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void connectingServer(String inputLine, BufferedReader streamReader, PrintWriter streamWriter){
        try {
            String[] portInfo = inputLine.split(":");
            streamWriter.println("wiem, że jesteś serwerem");
            streamReader.close();
            streamWriter.close();
            synchronized(serversQueue) {
                serversQueue.add(Integer.parseInt(portInfo[1]));
                serversQueue.notify();
                System.out.println(serversQueue);
            }
            connection.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Object sendAndRecieveCalculated(Object dataFrame, String action){
        System.out.println("zlecam watkowi");
        System.out.println(serversQueue);
        esSplitData.execute(new DataExchange(serversQueue, action, dataFrame));
        esSplitData.shutdown();
        try {
            boolean finished = esSplitData.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(dataFrame);
        return dataFrame;
    }

    public void connectingClient(String inputLine){
        try
        {
            ObjectInputStream objectInput = new ObjectInputStream(inputStream);
            Object object = objectInput.readObject();
            System.out.println((String)object);
            Object returning = sendAndRecieveCalculated(object, inputLine);
            System.out.println(returning);
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(returning);
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void processRequest(Socket requestToHandle) {
        synchronized (pool) {
            pool.add(pool.size(), requestToHandle);
            pool.notifyAll();
        }
    }
    public void run() {
        while (true) {
            synchronized (pool) {
                while (pool.isEmpty()) {
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                connection = (Socket) pool.remove(0);
            }
            handleConnection();
        }
    }
}