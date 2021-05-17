package projectSDU2.business.domain.initialize;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.user.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Transaction extends Thread {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private Command command;
    private static CreditingSystem creditingSystem = CreditingSystem.getInstance();


    public Transaction(Socket socket) {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        try {
            while (true) {
                command = (Command) inputStream.readObject();

                if (command.getCommand().equals(Commands.getAll)) {
                    ArrayList<SimpleObject> result = new ArrayList<>();
                    for (Production production : creditingSystem.getProductions()) {
                        result.add(new SimpleObject(production.getProductionID(), "Production", production.getName()));
                    }
                    for (Person person : creditingSystem.getPersons()) {
                        if(!person.getType().equals("systemadministrator")){
                            result.add(new SimpleObject(person.getId(), "Person", person.getName()));
                        }
                    }
                    outputStream.writeObject(result);
                    outputStream.flush();
                } else if (command.getCommand().equals(Commands.search)) {
                    ArrayList<SimpleObject> result = new ArrayList<>();
                    String searchString = command.getParameters().toLowerCase();
                    for (Production production : creditingSystem.getProductions()) {
                        String productionString = production.getName().toLowerCase();
                        if (productionString.contains(searchString)) {
                            result.add(new SimpleObject(production.getProductionID(), "Production", production.getName()));
                        }
                    }
                    for (Person person : creditingSystem.getPersons()) {
                        String personString = person.getName().toLowerCase();
                        if (personString.contains(searchString)) {
                            result.add(new SimpleObject(person.getId(), "Person", person.getName()));
                        }
                    }
                    outputStream.writeObject(result);
                    outputStream.flush();
                } else if (command.getCommand().equals(Commands.getObject)){
                    String[] received = command.getParameters().split(",");
                    HashMap<String, ArrayList<String>> result = new HashMap<>();
                    if(received[0].equals("Production")){
                        Production production = creditingSystem.findProduction(Integer.parseInt(received[1]));
                        ArrayList<String> name = new ArrayList<>();
                        name.add(production.getName());
                        result.put("Name", name);
                        ArrayList<String> company = new ArrayList<>();
                        company.add(production.getCompany());
                        result.put("Company", company);
                        ArrayList<String> credits = new ArrayList<>();
                        for (Credit credit : production.getCredits()){
                            credits.add(credit.getPerson().getName() + ": " + credit.getRoles().toString());
                        }
                        result.put("Credits", credits);
                    }else{
                        //person
                        Person person = creditingSystem.findPerson(Integer.parseInt(received[1]));
                        ArrayList<String> name = new ArrayList<>();
                        name.add(person.getName());
                        result.put("Name", name);
                        ArrayList<String> type = new ArrayList<>();
                        type.add(person.getType());
                        result.put("Type", type);
                        ArrayList<String> credits = new ArrayList<>();
                        for(Production production : creditingSystem.findProductionsForPerson(person.getId())){
                            for (Credit credit : production.getCredits()){
                                if(credit.getPerson().getId() == person.getId()){
                                    credits.add(production.getName() + ": " + credit.getRoles().toString());
                                }
                            }
                        }
                        result.put("Credits", credits);
                    }
                    outputStream.writeObject(result);
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
