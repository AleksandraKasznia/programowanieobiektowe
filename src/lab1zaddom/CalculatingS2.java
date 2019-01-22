package lab1zaddom;
import java.io.*;
import java.net.*;

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
        System.out.println(inputLine);
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);
        try {
            Object object = objectInput.readObject();
            String recievedDataFromClient = (String) object;
            System.out.println(recievedDataFromClient);
            recievedDataFromClient += "tyłeczek";
            ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutput.writeObject(recievedDataFromClient);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        /*
        if(inputLine.equals(myport)){
            streamReader.close();
            clientSocket.close();
            clientSocket = server.accept();
        }
        */
    }
}
