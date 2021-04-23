import java.io.*;
import java.net.*;

public class ServerThreadUDP extends Thread {

    private DatagramSocket socket;

    public ServerThreadUDP(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("(thread) Listening for client data:");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line of text
            System.out.println("(thread) Client wants file located at: '" + line + "'");
            

            File file = new File(line);

            OutputStream output = socket.getOutputStream();
            FileInputStream fileInput = new FileInputStream(file);
            BufferedInputStream buffInput = new BufferedInputStream(fileInput);
            byte byteArr[]  = new byte[(int)file.length()];
            buffInput.read(byteArr, 0, byteArr.length);

            System.out.println("(thread) Sending file to client: '"+ file + "(" + byteArr.length + " bytes)");

            output.write(byteArr,0,byteArr.length);
            output.flush();
            System.out.println("(thread) File sent");
  
            //close all the things
            output.close();
            input.close();
            reader.close();
            fileInput.close();
            buffInput.close();
            socket.close();
        }

        catch(IOException i)
        {
            System.out.println("(thread) I/O error: " + i.getMessage());
        }
    }
}