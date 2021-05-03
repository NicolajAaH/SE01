package projectSDU2.data;

import projectSDU2.Interfaces.PersistenceI;

import java.util.ArrayList;

public class PersistenceConnect implements PersistenceI {


    @Override
    public boolean findAccount(String email, String password) {
       return PersistenceHandler.findAccount(email, password);
    }

    @Override
    public String findType(String email) {
        return PersistenceHandler.findType(email);
    }

    @Override
    public ArrayList<String[]> getAccounts() {
        return PersistenceHandler.getAccounts();
    }


}
