package lab1zaddom;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DataExchange implements Runnable {
    protected static Queue<Integer> serversQueue;
    ArrayList<ArrayList> dataFrame;
    String action;
    protected Socket serverSocket;
    Object names;
    Object types;
    int port;
    String groupBy;

    DataExchange(final Queue<Integer> serversQueue, String action, ArrayList<ArrayList> dataFrame,
                 Object names, Object types, String groupBy){
        this.serversQueue = serversQueue;
        this.action = action;
        this.dataFrame = dataFrame;
        this.names = names;
        this.types = types;
        this.groupBy = groupBy;
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
            streamWriter.println(groupBy);
            System.out.println(groupBy);
            ObjectOutputStream objectOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            objectOutput.writeObject(dataFrame);
            objectOutput.writeObject(names);
            objectOutput.writeObject(types);
            ObjectInputStream objectInput = new ObjectInputStream(serverSocket.getInputStream());
            synchronized (dataFrame) {
                dataFrame.clear();
                dataFrame.addAll((ArrayList<ArrayList>)objectInput.readObject());
            }
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
            serversQueue.notify();
        }
    }
}
