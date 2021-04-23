import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class Client {
    
    private Socket socket = null;
    public static String fileLocation = "c:/temp/source.pdf";
    public static String fileDownload = "c:/temp/sourceDownload.pdf";
    public static int maxFileSize = 6022386;

    public Client(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            System.out.println("Client connected");
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

    public void receiveData(){

        try
        {
            System.out.println("Listening for server data:");
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line of text
            System.out.println("Server sent: '" + line + "'");
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
        Client client = new Client("localhost", 49153); //localhost or 127.0.0.1 can both be used for server on the same pc
        
        client.sendData(fileLocation);
        //client.receiveData();
        client.receiveFile();
        client.closeSocket();

        System.out.println("End of client");
    }
}
