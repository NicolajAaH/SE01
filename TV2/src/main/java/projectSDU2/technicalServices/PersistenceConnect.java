package projectSDU2.technicalServices;
import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.technicalServices.persistence.PersistenceFacade;

public class PersistenceConnect implements PersistenceI {


    @Override
    public PersistenceFacade getFacade() {
        return PersistenceFacade.getInstance();
    }




}
