package usersystem.business;

import projectSDU2.business.domain.initialize.Command;
import projectSDU2.business.domain.initialize.Commands;
import projectSDU2.business.domain.initialize.Server;
import projectSDU2.business.domain.initialize.SimpleObject;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client{

    private ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static Socket clientSocket;
    private static Client instance = new Client();
    public static Client getInstance(){
        return instance;
    }

    private Client(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(null);
            }
        }).start();
        try {
            clientSocket = new Socket("localhost", 6789);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SimpleObject> searchedObjects(String search){
        try {
            objectOutputStream.writeObject(new Command(Commands.search, search));
            return (ArrayList<SimpleObject>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SimpleObject> getAll() {
        try {
            objectOutputStream.writeObject(new Command(Commands.getAll, ""));
            return (ArrayList<SimpleObject>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, ArrayList<String>> getSpecific(String type, int id){
        try {
            objectOutputStream.writeObject(new Command(Commands.getObject, type + "," + id));
            return (HashMap<String, ArrayList<String>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

}
