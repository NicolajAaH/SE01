package projectSDU2.business.domain.credit;

import javafx.collections.ObservableList;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

public class Credit {
    private int creditID;
    private Person person;
    private ArrayList<Roles> roles;


    public Credit(int creditID, Person person, ArrayList<Roles> roles) {
        this.creditID = creditID;
        this.person = person;
        this.roles = roles;
    }

    public Credit(Person person, ArrayList<Roles> roles) {
        this.person = person;
        this.roles = roles;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

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
        for (int i = 0; i < roles.size(); i++) {
            rolesArrayList.add((Roles) roles.get(i));
            System.out.println(roles.get(i).toString());
        }
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: " + creditID + " Belongs to: " +person.getName()+ " Roles: ");
        for (Roles role : roles){
            stringBuilder.append(role.toString() + ", ");
        }
        return stringBuilder.toString();
    }
}
