package lab1zaddom;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        ArrayList <Integer> msg = new ArrayList();
        msg.add(2);
        DataFrame sentData = new DataFrame("test.csv",new Class[]{StringHolder.class, FloatHolder.class, FloatHolder.class, FloatHolder.class},true);
        DataFrame recievedData;

        try {
            echoSocket = new Socket("localhost", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }

        out.println("max");
        out.println("id");

        System.out.println("Type a message: ");
        ObjectOutputStream objectOutput = new ObjectOutputStream(echoSocket.getOutputStream());
        try {
            objectOutput.writeObject(sentData.dataFrame);
            objectOutput.writeObject(sentData.names);
            objectOutput.writeObject(sentData.types);
            System.out.println("Sending messages to the ServerSocket");
            ObjectInputStream objectInput = new ObjectInputStream(echoSocket.getInputStream());
            Object object = objectInput.readObject();
            System.out.println(object);
        }
        catch( ClassNotFoundException e){
            e.printStackTrace();
        }


        out.close();
        in.close();
        echoSocket.close();
    }
}
