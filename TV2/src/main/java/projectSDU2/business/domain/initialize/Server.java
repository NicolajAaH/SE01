package projectSDU2.business.domain.initialize;

import java.io.IOException;
import java.net.ServerSocket;

public class Server{
    private ServerSocket serverSocket;
    private Transaction transaction;
    private int port = 6789;

    public Server(){
        runServer();
    }

    public static void main(String[] args) {
        new Server();
    }

    public void runServer() {
        try{
            serverSocket = new ServerSocket(port);

            while(true){
                transaction = new Transaction(serverSocket.accept());
                transaction.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
