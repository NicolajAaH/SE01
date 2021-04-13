package projectSDU2.domain.user;

public abstract class Account {
    private String email;
    private String password;

    void authenticate(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void resetPassword(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
