package projectSDU2.business.domain.user;

public class Systemadministrator extends Person{

    public Systemadministrator(int ID, String name, int phone, String email, String password){
        this.name = name;
        this.phone = phone;
        this.id = ID;
        this.email = email;
        this.password = password;
    }
}
