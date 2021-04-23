import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Socket;

public class ServerUDP {
    
    public ServerUDP(int port) {


        try (DatagramSocket serverSocket = new DatagramSocket(port))
            {
            System.out.println("UDP server socket opened on port " + port);

            while(true) {
                System.out.println("Listening for incoming client requests:");
                DatagramSocket socket = serverSocket.accept();

                System.out.println("Client connected! " + socket);
                new ServerThreadUDP(socket).start();
                System.out.println("New thread started");
            }
         }
        catch(IOException i)
        {
            System.out.println("I/O error: " + i.getMessage());
        }
    }
}
