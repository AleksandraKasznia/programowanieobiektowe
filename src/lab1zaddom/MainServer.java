package lab1zaddom;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class MainServer {
    protected int maxConnections;
    protected int listenPort;

    Queue<Integer> serversQueue = new LinkedList<Integer>();
    public MainServer(int aListenPort, int maxConnections) {
        listenPort = aListenPort;
        this.maxConnections = maxConnections;
    }
    public void acceptConnections() {
        try {
            ServerSocket server = new ServerSocket(listenPort, 5);
            Socket incomingConnection;
            while (true) {
                incomingConnection = server.accept();
                handleConnection(incomingConnection);
            }
        } catch (BindException e) {
            System.out.println("Unable to bind to port " + listenPort);
        } catch (IOException e) {
            System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }
    }
    protected void handleConnection(Socket connectionToHandle) {
        PooledConnectionHandler.processRequest(connectionToHandle);
    }

    public static void main(String[] args) {
        MainServer server = new MainServer(3000, 3);
        server.setUpHandlers();

        server.acceptConnections();
    }
    public void setUpHandlers() {
        for (int i = 0; i < maxConnections; i++) {
            PooledConnectionHandler currentHandler = new PooledConnectionHandler(serversQueue);
            new Thread(currentHandler, "Handler " + i).start();
        }
    }

}


