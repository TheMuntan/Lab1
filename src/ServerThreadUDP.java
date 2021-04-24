import java.io.*;
import java.net.*;

public class ServerThreadUDP extends Thread {

    DatagramSocket socket = null;
    private byte[] byteArr = new byte[2048];
    private String received;
    InetAddress address;
    int port;

    public ServerThreadUDP(DatagramSocket socket, DatagramPacket packet, byte[] byteArr) {
        this.socket = socket;
        this.byteArr = byteArr;
        System.out.println("(thread) Unpacking received packet:");
        received = new String(packet.getData(), 0, packet.getLength());
        address = packet.getAddress();
        port = packet.getPort();
}

    @Override
    public void run() {
        try {          
            System.out.println("(thread) Received packet: " + received);
            byteArr = received.getBytes();
            DatagramPacket packetSend = new DatagramPacket(byteArr, byteArr.length, address, port);
            
            socket.send(packetSend);
        }
        catch(IOException i)
        {
            System.out.println("(thread) I/O error: " + i.getMessage());
        }
    
    }
}
