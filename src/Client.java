import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    
    private Socket socket = null;
    private boolean socketOpen = false;

    public Client(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            socketOpen = true;
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
            socketOpen = false;
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


    public static void main(String[] args) {
        Client client = new Client("localhost", 49153); //localhost or 127.0.0.1 can both be used for server on the same pc
        client.sendData("Yo waddup server bro!");
        client.receiveData();
        client.closeSocket();

        System.out.println("End of client");
    }
}
