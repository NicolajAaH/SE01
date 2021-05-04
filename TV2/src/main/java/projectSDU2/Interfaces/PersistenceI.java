package projectSDU2.Interfaces;

import projectSDU2.technicalServices.persistence.PersistenceFacade;

import java.util.ArrayList;

public interface PersistenceI {

    boolean findAccount(String email, String password);

    String findType(String email);

    PersistenceFacade getFacade();

}
