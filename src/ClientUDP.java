
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class ClientUDP {
    
    private DatagramSocket socket = null;
    public static String fileLocation = "c:/temp/source.pdf";
    public static String fileDownload = "c:/temp/sourceDownload.pdf";
    public static int maxFileSize = 6022386;
    static InetAddress server;

    public ClientUDP(String address, int port)
    {
        try
        {
            server = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            System.out.println("Client connected");
        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }

    }

    public void closeSocket() {
        try
        {
            socket.close();
            System.out.println("Client socket closed");
        }
        catch(UnknownHostException u)
        {
            System.out.println(u.getMessage());
        }
        catch(IOException i)
        {
            System.out.println(i.getMessage());
        }
    }

    public void sendData(String data){

        try
        {
            System.out.println("Sending data to server: " + data);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(data);
        }
        catch(UnknownHostException u)
        {
            System.out.println(u.getMessage());
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
            int bytesRead = 0;
            int counter = 0;
    
            byte byteArr[]  = new byte[maxFileSize];

            InputStream input = socket.getInputStream();
            FileOutputStream fileOutput = new FileOutputStream(fileDownload);
            BufferedOutputStream buffOutput = new BufferedOutputStream(fileOutput);

            bytesRead = input.read(byteArr, 0, byteArr.length);
            counter = bytesRead;

            do {
                bytesRead = input.read(byteArr, counter, (byteArr.length - counter));
                if (bytesRead >= 0)
                    counter += bytesRead;
            } while (bytesRead > -1);
    
            buffOutput.write(byteArr, 0 , counter);
            buffOutput.flush();
            System.out.println("File received: " + fileDownload + " (" + counter + " bytes)");
            fileOutput.close();
            buffOutput.close();
            socket.close();
        }
        catch(UnknownHostException u)
        {
            System.out.println("Server exception: " + u.getMessage());
        }
        catch(IOException i)
        {
            System.out.println("I/O error: " + i.getMessage());
        }

    }


    public static void main(String[] args) {
        ClientUDP client = new ClientUDP("localhost", 1234); //localhost or 127.0.0.1 can both be used for server on the same pc
        
        client.sendData(fileLocation);
        client.receiveFile();
        client.closeSocket();

        System.out.println("End of client");
    }
}
