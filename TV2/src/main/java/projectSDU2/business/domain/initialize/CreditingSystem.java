package projectSDU2.business.domain.initialize;

import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.technicalServices.PersistenceConnect;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.*;

import java.util.ArrayList;

public class CreditingSystem {
    //singleton pattern
    private static CreditingSystem instance = new CreditingSystem();

    private static PersistenceI persistenceI = new PersistenceConnect();


    public static PersistenceI getPersistenceI() {
        return persistenceI;
    }

    private CreditingSystem(){

    }

    public static CreditingSystem getInstance() {
        return instance;
    }

    private ArrayList<Production> productions = new ArrayList<>();
    private ArrayList<Person> persons = new ArrayList<>();

    public void setup(){
        //test participant
        persons.add(new Participant("testny", 12345678, "testny@asd.dk"));
        productions.add(new Production(1, new ArrayList<>(), "Company", "Name"));
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

    public ArrayList<Producer> getProducers() {
        ArrayList<Producer> producers = new ArrayList<>();
        persons.forEach(person -> {
            if(person instanceof Producer){
                producers.add((Producer) person);
            }
        });
        return producers;
    }

    public Roles[] getRoles(){
        Roles[] roles = Roles.values();
        return Roles.values();
    }

    public Person findPerson(int id) {
        for (Person person : persons){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }
}
