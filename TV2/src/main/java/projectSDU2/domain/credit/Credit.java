package projectSDU2.domain.credit;

import projectSDU2.domain.user.Person;

import java.util.ArrayList;

public class Credit {
    private int creditID;
    private Person person;
    private ArrayList<Roles> roles;
    private boolean status;
    private boolean sent;

    public Credit(int creditID, Person person, ArrayList<Roles> roles) {
        this.creditID = creditID;
        this.person = person;
        this.roles = roles;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
