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
    //Attributter
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private Command command;
    private static CreditingSystem creditingSystem = CreditingSystem.getInstance();

    //Constructor, der henter OutputStream og InputStream fra socket
    public Transaction(Socket socket) {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Det der køres når threaden starter
    @Override
    public void run() {
        connect();
    }

    //Henter kommando fra client og håndterer den
    private void connect() {
        try {//While true så den kører indtil der ikke er flere
            while (true) {
                command = (Command) inputStream.readObject(); //Læser kommandoen fra clienten

                //Tjekker hvilken type kommando det er
                if (command.getCommand().equals(Commands.getAll)) { //returnerer alle produktioner og personer (undtagen systemadministratore)
                    ArrayList<SimpleObject> result = new ArrayList<>();
                    for (Production production : creditingSystem.getProductions()) {
                        result.add(new SimpleObject(production.getProductionID(), "Production", production.getName()));
                    }
                    for (Person person : creditingSystem.getPersons()) {
                        if (!person.getType().equals("systemadministrator")) {
                            result.add(new SimpleObject(person.getId(), "Person", person.getName()));
                        }
                    }
                    outputStream.writeObject(result); //Giver objektet tilbage til clienten
                    outputStream.flush(); //Flusher for at sende det afsted
                } else if (command.getCommand().equals(Commands.search)) { //Søger efter i personer og produktioner efter et specifikt input
                    ArrayList<SimpleObject> result = new ArrayList<>();
                    String searchString = command.getParameters().toLowerCase(); //Ændrer det til lowercase for at gøre det case insensitive
                    for (Production production : creditingSystem.getProductions()) {
                        String productionString = production.getName().toLowerCase(); //Ændrer det til lowercase for at gøre det case insensitive
                        if (productionString.contains(searchString)) { //Hvis den indeholder strengen tilføjes den
                            result.add(new SimpleObject(production.getProductionID(), "Production", production.getName()));
                        }
                    }
                    for (Person person : creditingSystem.getPersons()) {
                        String personString = person.getName().toLowerCase(); //Ændrer det til lowercase for at gøre det case insensitive
                        if (personString.contains(searchString)) { //Hvis den indeholder strengen tilføjes den
                            result.add(new SimpleObject(person.getId(), "Person", person.getName()));
                        }
                    }
                    outputStream.writeObject(result); //Giver objektet tilbage til clienten
                    outputStream.flush(); //Flusher for at sende det afsted
                } else if (command.getCommand().equals(Commands.getObject)) { //Få specifikt objekt
                    String[] received = command.getParameters().split(","); //Splitter parametren op med , for at få de forskellige værdier
                    HashMap<String, ArrayList<String>> result = new HashMap<>();
                    if (received[0].equals("Production")) { //Produktion
                        Production production = creditingSystem.findProduction(Integer.parseInt(received[1])); //Finder produktion
                        ArrayList<String> name = new ArrayList<>();
                        name.add(production.getName());
                        result.put("Name", name);
                        ArrayList<String> company = new ArrayList<>();
                        company.add(production.getCompany());
                        result.put("Company", company);
                        ArrayList<String> credits = new ArrayList<>();
                        for (Credit credit : production.getCredits()) { //Henter alle credits for produktionen, og gør det mere læseligt, og konverterer til String
                            credits.add(credit.getPerson().getName() + ": " + credit.getRoles().toString());
                        }
                        result.put("Credits", credits); //Sætter alle attributterne i et HashMap der kan læses af forbrugersystemet
                    } else {
                        //person
                        Person person = creditingSystem.findPerson(Integer.parseInt(received[1])); //Finder person
                        ArrayList<String> name = new ArrayList<>();
                        name.add(person.getName());
                        result.put("Name", name);
                        ArrayList<String> type = new ArrayList<>();
                        type.add(person.getType());
                        result.put("Type", type);
                        ArrayList<String> credits = new ArrayList<>();
                        for (Production production : creditingSystem.findProductionsForPerson(person.getId())) { //finder alle krediteringer for en person
                            for (Credit credit : production.getCredits()) {
                                if (credit.getPerson().getId() == person.getId()) {
                                    credits.add(production.getName() + ": " + credit.getRoles().toString());
                                }
                            }
                        }
                        result.put("Credits", credits); //Sætter alle attributterne i et HashMap der kan læses af forbrugersystemet
                    }
                    outputStream.writeObject(result); //Giver objektet tilbage til clienten
                    outputStream.flush(); //Flusher for at sende det afsted
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
