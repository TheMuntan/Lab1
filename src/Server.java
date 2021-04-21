import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    
    public Server() {


        try 
        {
            ServerSocket serverSocket = new ServerSocket(49153); //private ports range from 49152 to 65535 
            System.out.println("Server socket opened");

            System.out.println("Listening for incoming client requests:");
            Socket socket = serverSocket.accept();

            System.out.println("Client connected!");

            System.out.println("Listening for client input:");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line of text
            System.out.println("Client sent: '" + line + "'");
         }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

    }
}
