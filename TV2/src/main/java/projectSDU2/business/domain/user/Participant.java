package projectSDU2.business.domain.user;


public class Participant extends Person {

    Participant(String name, int phone, int ID, String email){
        this.name = name;
        this.phone = phone;
        this.id = ID;
        this.email = email;
    }

    public Participant(String name, int phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Participant(int oid, String name, int phone, String email, String password) {
        this.id = oid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
