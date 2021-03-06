package projectSDU2.Interfaces;

import javafx.collections.ObservableList;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

public interface DomainI {
    //Interfacet beskriver hvilke metoder den grafiske brugergrænseflade (presentation) skal kunne tilgå i domænelaget
    //Alle metoderne er beskrevet i DomainConnect, se der. Ligger i business.domain

    boolean authorize(String email, String password);

    void runSetup();

    void addProducer(String name, int phone, String email, String password);

    ArrayList<Production> getProductions();

    Production findProduction(int id);

    ArrayList<Roles> getRoles();

    Credit createCredit(int id, ObservableList observableList);

    Roles findRole(String role);

    void addProduction(ObservableList observableList, String name, String company);

    Person findPerson(int id);

    Person findPerson(String email);

    ArrayList<Person> getProducers();

    Person castToPerson(Object object);

    ArrayList<Person> getParticipants();

    Production castToProduction(Object object);

    void addParticipant(String name, int phone, String email, String password);

    void editPerson(int oid, String name, int phone, String email, String password);

    Credit castToCredit(Object object);

    void editCredit(int productionID, int personID, ObservableList observableList);

    void editProduction(int oid, String name, String company, ObservableList credits, boolean status, boolean sent);

    void editPerson(int oid, String name, int phone, String email, String password, String type);

    void deletePerson(int id);

    void deleteProduction(int productionID);

    void deleteCredit(int creditID);

    void generateCreditingReport();

    ArrayList<Production> searchProductions(String search);

    ArrayList<Production> findWhereProducer(int producerID);

    ArrayList<Person> searchProducers(String search);

    ArrayList<Person> searchParticipants(String search);

    ArrayList<Production> getNotValidated();

    PersistenceI getPersistenceI();

    ArrayList<Person> getPersons();

    ArrayList<Person> searchPersons(String search);

    void merge(int person1ID, int person2ID, String name, int phone, String email, String password, String type);

    void generateFinanceReport();
}
