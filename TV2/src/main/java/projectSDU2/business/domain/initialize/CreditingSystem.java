package projectSDU2.business.domain.initialize;

import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.business.domain.report.CreditingReport;
import projectSDU2.technicalServices.PersistenceConnect;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.*;

import java.util.ArrayList;

public class CreditingSystem {
    //singleton pattern
    private static PersistenceI persistenceI = new PersistenceConnect();
    private static CreditingSystem instance = new CreditingSystem();



    public static PersistenceI getPersistenceI() {
        return persistenceI;
    }

    private CreditingSystem(){
        setup();
    }

    public void setup(){
        productions = new ArrayList<>();
        for (Object production : persistenceI.getFacade().getAll("productionmapper")){
            productions.add((Production) production);
        }

        persons = new ArrayList<>();
        for (Object person : persistenceI.getFacade().getAll("personmapper")){
            persons.add((Person) person);
        }

        rolesdb = new ArrayList<>();
        for (Object role : persistenceI.getFacade().getAll("rolemapper")){
            rolesdb.add((Roles) role);
        }

    }

    public static CreditingSystem getInstance() {
        return instance;
    }

    private ArrayList<Production> productions;
    private ArrayList<Person> persons;
    private ArrayList<Roles> rolesdb;

    public ArrayList<Roles> getRolesdb() {
        setup();
        return rolesdb;
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public ArrayList<Production> getProductions() {
        setup();
        return productions;
    }

    public ArrayList<Person> getPersons() {
        setup();
        return persons;
    }

    public void addProduction(Production production){
        productions.add(production);
    }

    public boolean authorizeAccount(String email, String password){
        for (Person person : persons){
            if(person.getEmail().equals(email) && person.getPassword().equals(password)){
                return true;
            }
        }
        return false;
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

    public void generateCreditingReport(){
        new CreditingReport().generateCreditingReport();
    }

    public void setSentProduction(Production production) {
        getPersistenceI().getFacade().edit(production.getProductionID(), production, "productionmapper");
    }
}
