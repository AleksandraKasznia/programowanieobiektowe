package lab1zaddom;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class CalculatingS2 {
    public static void main(String[] args) throws IOException {

        int myport = 6665;
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

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

        String userInput = "port:" + myport;

        out.println(userInput);
        System.out.println("echo: " + in.readLine());

        out.close();
        in.close();
        echoSocket.close();
        ServerSocket server = new ServerSocket(myport, 5);
        Socket clientSocket = server.accept();
        System.out.println("ktos sie polaczyl");
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine = streamReader.readLine();
        String groupBy = streamReader.readLine();
        System.out.println(inputLine);
        System.out.println(groupBy);
        String[] columns = groupBy.split(",");
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);
        try {
            Object object = objectInput.readObject();
            Object objectNames = objectInput.readObject();
            Object objectTypes = objectInput.readObject();
            ArrayList<ArrayList> recievedDataFromClient = (ArrayList<ArrayList>) object;
            String[] names = (String[])objectNames;
            Class<Value>[] types = (Class<Value>[])objectTypes;
            System.out.println(recievedDataFromClient);
            DataFrame dataFrame = new DataFrame(recievedDataFromClient, names, types);
            DataFrame counted = null;
            switch (inputLine){
                case "max":
                    counted = dataFrame.groupby(columns).max();
            }

            ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutput.writeObject(counted.dataFrame);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }


}
