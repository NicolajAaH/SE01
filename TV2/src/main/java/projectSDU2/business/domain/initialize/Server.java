package projectSDU2.business.domain.initialize;

import java.io.IOException;
import java.net.ServerSocket;

public class Server{
    //Attributter
    private ServerSocket serverSocket;
    private Transaction transaction;
    private int port = 6789;

    //Constructor, som starter runServer
    public Server(){
        runServer();
    }

    //En main metode til at køre serveren
    public static void main(String[] args) {
        new Server();
    }

    //Starter selve serveren
    public void runServer() {
        try{
            serverSocket = new ServerSocket(port);

            //While true loop for at kunne fortsætte med at tage imod flere transaction, hvorefter den transaction thread startes
            while(true){
                transaction = new Transaction(serverSocket.accept());
                transaction.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
