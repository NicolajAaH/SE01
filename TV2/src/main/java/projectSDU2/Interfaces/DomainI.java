package projectSDU2.Interfaces;

import javafx.collections.ObservableList;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;
import java.util.Collection;

public interface DomainI {

    //tilføj hvad GUI skal kunne tilgå
    boolean authorize(String email, String password);

    String findType(String email);


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
}
