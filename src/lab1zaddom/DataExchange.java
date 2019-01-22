package lab1zaddom;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class DataExchange implements Runnable {
    protected static Queue<Integer> serversQueue;
    protected static Object dataFrame;
    String action;
    protected Socket serverSocket;
    int port;

    DataExchange(final Queue<Integer> serversQueue, String action,final Object dataFrame){
        this.serversQueue = serversQueue;
        this.action = action;
        this.dataFrame = dataFrame;
        System.out.println(serversQueue);
    }

    public void connectToServer(){
        synchronized (serversQueue){
            while (serversQueue.isEmpty()){
                try {
                    serversQueue.wait();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            port = serversQueue.poll();
        }
        try {
            serverSocket = new Socket("localhost", port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendData(){
        try {
            PrintWriter streamWriter = new PrintWriter(serverSocket.getOutputStream(), true);
            streamWriter.println(action);
            ObjectOutputStream objectOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            objectOutput.writeObject(dataFrame);
            ObjectInputStream objectInput = new ObjectInputStream(serverSocket.getInputStream());
            synchronized (dataFrame) {
                dataFrame = objectInput.readObject();
            }
            System.out.println(dataFrame);
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        connectToServer();
        sendData();
        synchronized (serversQueue){
            serversQueue.add(port);
            System.out.println(dataFrame);
            serversQueue.notify();
            System.out.println(dataFrame);
        }
    }
}
