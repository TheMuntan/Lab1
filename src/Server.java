import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    public Server(int port) {


        try (ServerSocket serverSocket = new ServerSocket(port))
            {
            System.out.println("Server socket opened on port " + port);

            while(true) {
                System.out.println("Listening for incoming client requests:");
                Socket socket = serverSocket.accept();

                System.out.println("Client connected!");
                new ServerThread(socket).start();
                System.out.println("New thread started");
            }
         }
        catch(IOException i)
        {
            System.out.println("I/O error: " + i.getMessage());
        }
    }
}
