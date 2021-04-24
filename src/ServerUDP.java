import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class ServerUDP {
    
    public ServerUDP(int port) {

        byte[] byteArr = new byte[2048];
        DatagramPacket packet = new DatagramPacket(byteArr, byteArr.length);

        try (DatagramSocket serverSocket = new DatagramSocket(port))
            {
            System.out.println("UDP server socket opened on port " + port);

            while(true) {
                System.out.println("Listening for incoming client packets:");
                serverSocket.receive(packet);

                System.out.println("Packet received!");
                System.out.println("New thread started");
                new ServerThreadUDP(serverSocket, packet, byteArr).start();
        
            }
         }
        catch(IOException i)
        {
            System.out.println("I/O error: " + i.getMessage());
        }
    }
}
