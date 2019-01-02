package lab1zaddom;
import java.io.*;
import java.net.*;

public class CalculatingServer {
    public static void main(String[] args) throws IOException {

        int myport = 6666;
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
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = streamReader.readLine();
        System.out.println(inputLine);
        streamReader.close();
        server.close();

    }
}
