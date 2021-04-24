
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class ClientUDP {
    
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private static String fileLocation = "c:/temp/source.pdf";
    private static String fileDownload = "c:/temp/sourceDownload.pdf";
    private static int maxFileSize = 6022386;
    private int port = 1234;
    private InetAddress serverAddress;
    private byte[] byteArr = new byte[2048];

    public ClientUDP(String address)
    {
        try
        {
            serverAddress = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            System.out.println("Client connected");
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
            byteArr = data.getBytes();
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

    // public void receiveFile(){

    //     try
    //     {
    //         System.out.println("Receiving file from server");
    //         int bytesRead = 0;
    //         int counter = 0;
    
    //         byte byteArr[]  = new byte[maxFileSize];

    //         InputStream input = socket.getInputStream();
    //         FileOutputStream fileOutput = new FileOutputStream(fileDownload);
    //         BufferedOutputStream buffOutput = new BufferedOutputStream(fileOutput);

    //         bytesRead = input.read(byteArr, 0, byteArr.length);
    //         counter = bytesRead;

    //         do {
    //             bytesRead = input.read(byteArr, counter, (byteArr.length - counter));
    //             if (bytesRead >= 0)
    //                 counter += bytesRead;
    //         } while (bytesRead > -1);
    
    //         buffOutput.write(byteArr, 0 , counter);
    //         buffOutput.flush();
    //         System.out.println("File received: " + fileDownload + " (" + counter + " bytes)");
    //         fileOutput.close();
    //         buffOutput.close();
    //         socket.close();
    //     }
    //     catch(IOException i)
    //     {
    //         System.out.println("I/O error: " + i.getMessage());
    //     }

    // }


    public static void main(String[] args) {
        ClientUDP client = new ClientUDP("localhost"); //localhost or 127.0.0.1 can both be used for server on the same pc
        
        //client.sendData(fileLocation);
        // client.receiveFile();
        client.sendData("Yo whaddup udp server bro?");
        client.receiveData();
        client.closeSocket();

        System.out.println("End of client");
    }
}
