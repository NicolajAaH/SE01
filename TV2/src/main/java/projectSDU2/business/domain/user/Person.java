package projectSDU2.business.domain.user;

public class Person {
    String name;
    int id;
    int phone;
    String email;
    String password;
    String type;

    public Person(String name, int id, int phone, String email, String password, String type) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public Person(String name, int phone, String email, String password, String type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString(){
        return "ID: "+id+"\t"+"Name :"+name+"\t"+"Phone: "+phone+"\t"+"Email: "+email+"\t"+"Type: "+type;
    }
}
