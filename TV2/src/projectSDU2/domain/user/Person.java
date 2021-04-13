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
}
