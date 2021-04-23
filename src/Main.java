public class Main {
    public static void main(String[] args) {
        int port = 49153; //private ports range from 49152 to 65535
        Server server = new Server(port);
        //Client client = new Client("127.0.0.1", 49153);
        //client.closeSocket();
        System.out.println("End of server");
    }

}

/*
https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
https://www.geeksforgeeks.org/socket-programming-in-java/
https://stackoverflow.com/questions/32251895/java-file-transfer-file-to-server
https://coderanch.com/t/556838/java/Transferring-file-file-data-socket
https://www.rgagnon.com/javadetails/java-0542.html


*/
