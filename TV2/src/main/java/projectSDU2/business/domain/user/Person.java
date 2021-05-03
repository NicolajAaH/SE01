package projectSDU2.domain.user;

public abstract class Person {
    String name;
    int id;
    int phone;
    String email;

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
}
