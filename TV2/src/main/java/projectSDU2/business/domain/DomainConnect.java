package projectSDU2.business.domain;

import javafx.collections.ObservableList;
import projectSDU2.Interfaces.DomainI;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

public class DomainConnect implements DomainI {

    private CreditingSystem creditingSystem = CreditingSystem.getInstance();

    @Override
    public boolean authorize(String email, String password) {
        return creditingSystem.authorizeAccount(email, password);
    }

    @Override
    public String findType(String email) {
        return creditingSystem.findType(email);
    }

    @Override
    public void runSetup() {

    }

    @Override
    public void addProducer(String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().put(new Person(name, phone, email, password, "producer"), "personmapper");

    }


    @Override
    public ArrayList<Production> getProductions() {
        return creditingSystem.getProductions();
    }

    @Override
    public Production findProduction(int id) {
        return creditingSystem.findProduction(id);
    }

    @Override
    public ArrayList<Roles> getRoles() {
        return creditingSystem.getRolesdb();
    }

    @Override
    public Credit createCredit(int id, ObservableList observableList) {
        int nextInt = CreditingSystem.getPersistenceI().getFacade().getNextInt("creditmapper");
        ArrayList<Roles> roles = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            roles.add((Roles) observableList.get(i));
        }

        Person person = findPerson(id);

        return new Credit(nextInt, person, roles);
    }

    @Override
    public Roles findRole(String role) {
        return Roles.valueOf(role);
    }

    @Override
    public void addProduction(ObservableList observableList, String name, String company) {
        ArrayList<Credit> credits = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            credits.add((Credit) observableList.get(i));
        }
        CreditingSystem.getPersistenceI().getFacade().put(new Production(credits, company, name), "productionmapper");
    }

    @Override
    public Person findPerson(int id) {
        return creditingSystem.findPerson(id);
    }

    @Override
    public Person findPerson(String email) {
        return creditingSystem.findPerson(email);
    }

    @Override
    public ArrayList<Person> getProducers() {
        ArrayList<Person> producers = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()){
            if(person.getType().equals("producer")){
                producers.add(person);
            }
        }
        return producers;
    }

    @Override
    public Person castToPerson(Object object) {
        return (Person) object;
    }

    @Override
    public ArrayList<Person> getParticipants() {
        ArrayList<Person> participants = new ArrayList<>();
        for (Person person: creditingSystem.getPersons()){
            if(person.getType().equals("participant")){
                participants.add(person);
            }
        }
        return participants;
    }

    @Override
    public Production castToProduction(Object object) {
        return (Production) object;
    }

    @Override
    public void addParticipant(String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().put(new Person(name, phone, email, password, "participant"), "personmapper");
    }

    @Override
    public void editPerson(int oid, String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Person(name, phone, email, password, "person"), "personmapper");
    }

    @Override
    public Credit castToCredit(Object object) {
        return (Credit) object;
    }

    @Override
    public void editCredit(int productionID, int personID, ObservableList observableList) {
        ArrayList<Roles> roles = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            roles.add((Roles) observableList.get(i));
        }
        Person person = findPerson(personID);
        CreditingSystem.getPersistenceI().getFacade().edit(productionID, new Credit(person, roles), "creditmapper");
    }

    @Override
    public void editProduction(int oid, String name, String company, ObservableList credits, boolean status, boolean sent) {
        ArrayList<Credit> creditsList = new ArrayList<>();
        for (int i = 0; i < credits.size(); i++) {
            creditsList.add((Credit) credits.get(i));
        }
        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Production(creditsList, company, name, status, sent), "productionmapper");
    }

    @Override
    public void editPerson(int oid, String name, int phone, String email, String password, String type) {

        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Person(name, phone, email, password, type), "personmapper");
    }

    @Override
    public void deletePerson(int id) {
        CreditingSystem.getPersistenceI().getFacade().delete(id, "personmapper");
    }

    @Override
    public void deleteProduction(int productionID) {
        CreditingSystem.getPersistenceI().getFacade().delete(productionID, "productionmapper");
    }

    @Override
    public void deleteCredit(int creditID) {
        CreditingSystem.getPersistenceI().getFacade().delete(creditID, "creditmapper");
    }


}
