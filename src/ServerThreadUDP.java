import java.io.*;
import java.net.*;

public class ServerThreadUDP extends Thread {

    DatagramSocket socket = null;
    private byte[] byteArr = new byte[18432];
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

            byteArr = readFileToByteArray(received);

            DatagramPacket packetSend = new DatagramPacket(byteArr, byteArr.length, address, port);
            socket.send(packetSend);        
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
            int read = fileInput.read(byteArr);
            fileInput.close();
            System.out.println(read);
        } catch (IOException i) {
            i.printStackTrace();
        }
        return byteArr;
    }

}
