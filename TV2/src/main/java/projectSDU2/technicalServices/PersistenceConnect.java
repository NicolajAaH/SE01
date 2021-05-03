package projectSDU2.technicalServices;
import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.technicalServices.persistence.PersistenceFacade;

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
    public PersistenceFacade getFacade() {
        return PersistenceFacade.getInstance();
    }


}
