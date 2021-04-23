import java.io.*;
import java.net.*;

public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("(thread) Listening for client data:");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line of text
            System.out.println("(thread) Client sent: '" + line + "'");
            
            System.out.println("(thread) Sending data to client");

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println("Nothing much, wbu?");
        }
        catch(IOException i)
        {
            System.out.println("(thread) I/O error: " + i.getMessage());
        }
    }
}
