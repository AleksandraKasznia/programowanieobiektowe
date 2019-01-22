package lab1zaddom;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String msg = "dupadupa";
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

        out.println("maxi");

        System.out.println("Type a message: ");
        ObjectOutputStream objectOutput = new ObjectOutputStream(echoSocket.getOutputStream());
        try {
            objectOutput.writeObject(msg);
            System.out.println("Sending messages to the ServerSocket");
            ObjectInputStream objectInput = new ObjectInputStream(echoSocket.getInputStream());
            Object object = objectInput.readObject();
            String cievedData =  (String) object;
            System.out.println(cievedData);
        }
        catch( ClassNotFoundException e){
            e.printStackTrace();
        }


        out.close();
        in.close();
        echoSocket.close();
    }
}
