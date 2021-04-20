package projectSDU2.domain.user;

public abstract class Account {
    private String email;
    private String password;


    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    void authenticate(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void resetPassword(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAccountEmail(String email) {
        this.email = email;
    }

    public void setAccountPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
