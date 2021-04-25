import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ClientUDP {
    
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private static String fileLocation = "c:/temp/source.pdf";
    private static String fileDownload = "c:/temp/sourceDownloadUDP.pdf";
    private int port = 1234;
    private InetAddress serverAddress;
    private byte[] byteArr2 = new byte[65535]; //maximum theoretical UDP packet size is 65535 bytes

    public ClientUDP(String address)
    {
        try
        {
            serverAddress = InetAddress.getByName(address);
            socket = new DatagramSocket();
            System.out.println("Socket created");
        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }
    }

    public void closeSocket() {
            socket.close();
            System.out.println("Client socket closed");
    }

    public void sendData(String data){

        try
        {
            System.out.println("Sending data to server: " + data);
            byte[] byteArr = data.getBytes();
            packet = new DatagramPacket(byteArr, byteArr.length, serverAddress, port);
            socket.send(packet);
        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }
    }

    public void receiveData(){

        try
        {
            System.out.println("Receiving data from server");
            socket.receive(packet);
            serverAddress = packet.getAddress();
            port = packet.getPort();
            System.out.println("Server address: " + serverAddress);
            System.out.println("Port: " + port);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: '" + received + "'");

        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }
    }

    public void receiveFile(){

        try
        {
            System.out.println("Receiving file from server");
            DatagramPacket filePacket = new DatagramPacket(byteArr2, byteArr2.length, serverAddress, port);
            socket.receive(filePacket);
            serverAddress = filePacket.getAddress();
            port = filePacket.getPort();
            System.out.println("File received from address " + serverAddress + " and on port " + port);

            byteArr2 = filePacket.getData();
            
            File file = new File(fileDownload); // Creating the file
            FileOutputStream fileOutput = new FileOutputStream(file); // Creating the stream through which we write the file content
            
            fileOutput.write(byteArr2);
            fileOutput.close();

        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }
    }


    public static void main(String[] args) {
        ClientUDP client = new ClientUDP("localhost"); //localhost or 127.0.0.1 can both be used for server on the same pc
        
        client.sendData(fileLocation);
        client.receiveFile();
        //client.sendData("Yo whaddup udp server bro?");
        //client.receiveData();
        client.closeSocket();

        System.out.println("End of client");
    }
}
