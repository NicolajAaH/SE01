package projectSDU2.domain.initialize;

import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.data.PersistenceConnect;
import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.user.*;

import java.util.ArrayList;

public class CreditingSystem {
    //singleton pattern
    private static CreditingSystem instance = new CreditingSystem();

    private static PersistenceI persistenceI = new PersistenceConnect();

    private CreditingSystem(){

    }

    public static CreditingSystem getInstance() {
        return instance;
    }

    private ArrayList<Production> productions = new ArrayList<>();
    private ArrayList<Account> accounts;
    private ArrayList<Person> persons = new ArrayList<>();

    public void setup(){
        accounts = new ArrayList<>();
        for (int i = 0; i < persistenceI.getAccounts().size(); i++) {
            if(persistenceI.getAccounts().get(i)[2].equals("systemadministrator")) {
                accounts.add(new SystemAdministratorLogin(persistenceI.getAccounts().get(i)[0], persistenceI.getAccounts().get(i)[1]));
            }
            else if(persistenceI.getAccounts().get(i)[2].equals("producer")){
                ArrayList<Production> productions = new ArrayList<>();
                productions.add(new Production(1, new ArrayList<>()));
                productions.add(new Production(4, new ArrayList<>()));
                productions.add(new Production(9, new ArrayList<>()));
                productions.add(new Production(11, new ArrayList<>()));
                accounts.add(new ProducerLogin(new Producer("name", 12345678, persistenceI.getAccounts().get(i)[0],
                        "asdfghjk", productions), persistenceI.getAccounts().get(i)[1]));
            }
            else if(persistenceI.getAccounts().get(i)[2].equals("participant")){
                accounts.add(new ParticipantLogin(new Participant("name", 12345678, persistenceI.getAccounts().get(i)[0]),
                        persistenceI.getAccounts().get(i)[1]));
            }
        }
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void addProduction(Production production){
        productions.add(production);
    }

    public Account findAccount(String email){
        for (Account account : accounts){
            if(account.getEmail().equals(email)){
                return account;
            }
        }
        return null;
    }

    public boolean authorizeAccount(String email, String password){
        return persistenceI.findAccount(email, password);
    }

    public String findType(String email){
        return persistenceI.findType(email);
    }

    public Person findPerson(String email){
        for (Person person : persons){
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        return null;
    }

    public void removeAccount(String email){
        accounts.remove(findAccount(email));
    }

    public Production findProduction(int productionID){
        for (Production production : productions){
            if(production.getProductionID() == productionID){
                return production;
            }
        }
        return null;
    }

    public void removeProduction(int productionID){
        productions.remove(findProduction(productionID));
    }
}
