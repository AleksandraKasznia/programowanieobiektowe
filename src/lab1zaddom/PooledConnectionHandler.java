package lab1zaddom;
import java.io.*;
import java.net.*;
import java.util.*;
public class PooledConnectionHandler implements Runnable {
    protected Socket connection;
    protected static List pool = new LinkedList();
    public PooledConnectionHandler() {
    }

    public void connectToServer(int port){
        try {
            Socket echoSocket = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println("połączyłem "+port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void handleConnection() {
        try {
            PrintWriter streamWriter = new PrintWriter(connection.getOutputStream(),true);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = streamReader.readLine();
            String port = inputLine.substring(0,4);
            if (port.equals("port")){
                String[] portInfo = inputLine.split(":");
                streamWriter.println("wiem, że jesteś serwerem");
                streamReader.close();
                streamWriter.close();
                connectToServer(Integer.parseInt(portInfo[1]));

            }
            while (inputLine != null) {
                streamWriter.println(inputLine);
                inputLine = streamReader.readLine();
            }
            streamWriter.close();
            streamReader.close();

        }
        catch (IOException e){
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