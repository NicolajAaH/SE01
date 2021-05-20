package projectSDU2.business.domain.initialize;

import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.report.CreditingReport;
import projectSDU2.business.domain.report.FinanceReport;
import projectSDU2.technicalServices.PersistenceConnect;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.*;

import java.util.ArrayList;

public class CreditingSystem {
    //singleton pattern
    private static PersistenceI persistenceI = new PersistenceConnect();
    private static CreditingSystem instance = new CreditingSystem();


    //Returnerer værdien for singleton
    public static PersistenceI getPersistenceI() {
        return persistenceI;
    }

    public static CreditingSystem getInstance() {
        return instance;
    }


    //Constructor for singleton
    private CreditingSystem() {
        setup();
    }

    //Attributter
    private ArrayList<Production> productions;
    private ArrayList<Person> persons;
    private ArrayList<Roles> rolesdb;

    //Henter produktioner, personer og rollerne fra databasen og ligger dem i listerne
    public void setup() {
        productions = new ArrayList<>();
        for (Object production : persistenceI.getFacade().getAll("productionmapper")) {
            productions.add((Production) production);
        }

        persons = new ArrayList<>();
        for (Object person : persistenceI.getFacade().getAll("personmapper")) {
            persons.add((Person) person);
        }

        rolesdb = new ArrayList<>();
        for (Object role : persistenceI.getFacade().getAll("rolemapper")) {
            rolesdb.add((Roles) role);

        }

    }

    //Getters & Setters
    public ArrayList<Roles> getRolesdb() {
        setup();
        return rolesdb;
    }

    public ArrayList<Production> getProductions() {
        setup();
        return productions;
    }

    public ArrayList<Person> getPersons() {
        setup();
        return persons;
    }

    //Autoriserer brugeren ved at søge på deres email, og derefter sammenligne med password
    public boolean authorizeAccount(String email, String password) {
        for (Person person : persons) {
            if (person.getEmail().equals(email) && person.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Finder en person ud fra en email
    public Person findPerson(String email) {
        for (Person person : persons) {
            if (person.getEmail().equals(email)) {
                return person;
            }
        }
        return null;
    }

    //Finder produktion ud fra et id
    public Production findProduction(int productionID) {
        for (Production production : productions) {
            if (production.getProductionID() == productionID) {
                return production;
            }
        }
        return null;
    }

    //Returnerer alle roller
    public Roles[] getRoles() {
        return Roles.values();
    }

    //Find person ud fra id
    public Person findPerson(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    //Genererer krediteringsrapport
    public void generateCreditingReport() {
        new CreditingReport().generateCreditingReport();
    }

    //Ændrer så en production står som 'sent'
    public void setSentProduction(Production production) {
        getPersistenceI().getFacade().edit(production.getProductionID(), production, "productionmapper");
    }

    //Finder alle de produktioner hvor en producer er producer på, ud fra et id
    public ArrayList<Production> findWhereProducer(int producerID) {
        ArrayList<Production> matchingProductions = new ArrayList<>();
        for (Production production : productions) {
            for (Credit credit : production.getCredits()) {
                if (credit.getPerson().getId() == producerID) {
                    for (Roles role : credit.getRoles()) {
                        if (role == Roles.Producer) { //Tjekker om rollen for hver kreditering i hver produktion er Producer, og hvis den er tilføjes den til listen
                            matchingProductions.add(production);
                        }
                    }
                }
            }
        }
        return matchingProductions;
    }

    //Finder alle krediteringer for en person ud fra et id af personen
    public ArrayList<Credit> findCreditsForPerson(int personID) {
        ArrayList<Credit> creditArrayList = new ArrayList<>();
        for (Production production : productions) {
            for (Credit credit : production.getCredits()) {
                if (credit.getPerson().getId() == personID) {
                    creditArrayList.add(credit);
                }
            }
        }
        return creditArrayList;
    }

    //Finder produktioner som en person er med i, ud fra et ID af en person
    public ArrayList<Production> findProductionsForPerson(int personID) {
        ArrayList<Production> productionArrayList = new ArrayList<>();
        for (Production production : productions) {
            for (Credit credit : production.getCredits()) {
                if (credit.getPerson().getId() == personID) {
                    productionArrayList.add(production);
                }
            }
        }
        return productionArrayList;
    }

    //Genererer finansrapport
    public void generateFinanceReport(){
        new FinanceReport().generateFinanceReport();
    }
}
