package projectSDU2.Interfaces;

import javafx.collections.ObservableList;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;
import projectSDU2.business.domain.user.Producer;

import java.util.ArrayList;

public interface DomainI {

    //tilføj hvad GUI skal kunne tilgå
    boolean authorize(String email, String password);

    String findType(String email);


    void runSetup();

    void addProducer();

    Producer findProducer(String email);

    ArrayList<Producer> getProducers();

    ArrayList<Production> getProductions();

    Production findProduction(int id);

    Roles[] getRoles();

    Credit createCredit(int id, Person person, ObservableList observableList);

    Roles findRole(String role);

    Production createProduction(int id, ObservableList observableList, String name, String company);

    Person findPerson(int id);

    Person findPerson(String email);
}
