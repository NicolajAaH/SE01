package projectSDU2.Interfaces;

import java.util.ArrayList;

public interface PersistenceI {

    boolean findAccount(String email, String password);

    String findType(String email);

    ArrayList<String[]> getAccounts();
}
