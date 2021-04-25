import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThreadUDP extends Thread {

    DatagramSocket socket = null;
    private String received;
    InetAddress address;
    int port;

    public ServerThreadUDP(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;
        System.out.println("(thread) Unpacking received packet:");
        received = new String(packet.getData(), 0, packet.getLength());
        address = packet.getAddress();
        port = packet.getPort();
    }

    @Override
    public void run() {
        try {          
            System.out.println("(thread) Received packet: " + received);

            byte[] byteArr = readFileToByteArray(received);

            DatagramPacket packetSend = new DatagramPacket(byteArr, byteArr.length, address, port);
            socket.send(packetSend);        
            System.out.println("Packet sent to address " + address + " and on port " + port);
        }
        catch(IOException i)
        {
            System.out.println("(thread) I/O error: " + i.getMessage());
        }
    
    }

    public static byte[] readFileToByteArray(String fileName) {
        FileInputStream fileInput = null;
        File file = new File(fileName);
        byte[] byteArr = new byte[(int)file.length()];

        try {
            fileInput = new FileInputStream(file);
            fileInput.read(byteArr);
            fileInput.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return byteArr;
    }

}
