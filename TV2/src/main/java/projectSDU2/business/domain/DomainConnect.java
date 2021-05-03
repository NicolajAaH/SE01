package projectSDU2.domain;

import javafx.collections.ObservableList;
import projectSDU2.Interfaces.DomainI;
import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.credit.Roles;
import projectSDU2.domain.initialize.CreditingSystem;
import projectSDU2.domain.user.Account;
import projectSDU2.domain.user.Person;
import projectSDU2.domain.user.Producer;

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
    public Account findAccount(String email) {
        return creditingSystem.findAccount(email);
    }

    @Override
    public void runSetup() {
        creditingSystem.setup();
    }

    @Override
    public void addProducer() {

    }

    @Override
    public Producer findProducer(String email) {
        return (Producer) creditingSystem.findPerson(email);
    }

    @Override
    public ArrayList<Producer> getProducers() {
        return creditingSystem.getProducers();
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
    public Roles[] getRoles() {
        return creditingSystem.getRoles();
    }

    @Override
    public Credit createCredit(int id, Person person, ObservableList observableList) {
        ArrayList<Roles> roles = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            roles.add((Roles) observableList.get(i));
        }

        return new Credit(id, person, roles);
    }

    @Override
    public Roles findRole(String role) {
        return Roles.valueOf(role);
    }

    @Override
    public Production createProduction(int id, ObservableList observableList, String name, String company) {
        ArrayList<Credit> credits = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            credits.add((Credit) observableList.get(i));
        }
        return new Production(id, credits, company, name);
    }

    @Override
    public Person findPerson(int id) {
        return creditingSystem.findPerson(id);
    }


}
