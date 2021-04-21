import java.io.IOException;
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
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
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
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
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
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

    }


    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 49153);
        client.sendData("Yo waddup server bro!");
        client.closeSocket();

        System.out.println("End of client");
    }
}
