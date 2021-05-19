package projectSDU2.business.domain.credit;

import javafx.collections.ObservableList;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

public class Credit {
    //attributter
    private int creditID;
    private Person person;
    private ArrayList<Roles> roles;

    //constructors
    public Credit(int creditID, Person person, ArrayList<Roles> roles) {
        this.creditID = creditID;
        this.person = person;
        this.roles = roles;
    }

    public Credit(Person person, ArrayList<Roles> roles) {
        this.person = person;
        this.roles = roles;
    }

    //getters & setters
    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoles(ArrayList<Roles> roles) {
        this.roles = roles;
    }

    public int getCreditID() {
        return creditID;
    }

    public Person getPerson() {
        return person;
    }

    public ArrayList<Roles> getRoles() {
        return roles;
    }

    public void setRoles(ObservableList roles) {
        ArrayList<Roles> rolesArrayList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) { //iterer igennem ObservableList for at konvertere det til roller
            rolesArrayList.add((Roles) roles.get(i));
            System.out.println(roles.get(i).toString());
        }
    }

    //Overrided toString metoden
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(); //til at bygge strengen
        stringBuilder.append("ID: " + creditID + " Belongs to: " + person.getName() + " Roles: ");
        for (Roles role : roles) {
            stringBuilder.append(role.toString() + ", "); //tilføj hver rolle i strengen
        }
        return stringBuilder.toString();
    }
}
