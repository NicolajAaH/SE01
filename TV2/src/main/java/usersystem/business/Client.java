package usersystem.business;

import projectSDU2.business.domain.initialize.Command;
import projectSDU2.business.domain.initialize.Commands;
import projectSDU2.business.domain.initialize.Server;
import projectSDU2.business.domain.initialize.SimpleObject;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {

    //Attributter
    private ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static Socket clientSocket;
    private static Client instance = new Client();

    //Returnerer instansen, singleton pattern
    public static Client getInstance() {
        return instance;
    }

    //Privat constructor, singleton pattern
    private Client() {
        new Thread(new Runnable() { //Opretter threaden der kører serveren
            @Override
            public void run() {
                Server.main(null); //Starter serveren
            }
        }).start(); //Starter threaden
        try {
            clientSocket = new Socket("localhost", 6789); //Indstiller port
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream()); //Henter OutputStream fra client
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream()); //Henter InputStream fra client
        } catch (IOException e) {

        }
    }

    //Søger efter personer og krediteringer ud fra en streng
    public List<SimpleObject> searchedObjects(String search) {
        try {
            objectOutputStream.writeObject(new Command(Commands.search, search)); //Opretter en kommando med søgestrengen
            return (ArrayList<SimpleObject>) objectInputStream.readObject(); //Returnerer den liste serveren giver tilbage
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; //Fejl
    }

    //Henter alle produktioner og personer (undtagen systemadmins)
    public List<SimpleObject> getAll() {
        try {
            objectOutputStream.writeObject(new Command(Commands.getAll, "")); //Opretter en kommando der henter alle
            return (ArrayList<SimpleObject>) objectInputStream.readObject(); //Returnerer den liste serveren giver tilbage
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; //Fejl
    }

    //Henter specifik person/produktion
    public HashMap<String, ArrayList<String>> getSpecific(String type, int id) {
        try {
            objectOutputStream.writeObject(new Command(Commands.getObject, type + "," + id)); //Opretter en kommando med type og id, server finder specifik.
            return (HashMap<String, ArrayList<String>>) objectInputStream.readObject(); //Serveren giver det specifikke objekt tilbage i et HashMap med attributter
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; //Fejl
    }
}
