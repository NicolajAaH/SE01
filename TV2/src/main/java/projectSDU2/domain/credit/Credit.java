package projectSDU2.domain.credit;

import projectSDU2.domain.user.Person;

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

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoles(ArrayList<Roles> roles) {
        this.roles = roles;
    }
}
