package projectSDU2.technicalServices;

import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.technicalServices.persistence.PersistenceFacade;

public class PersistenceConnect implements PersistenceI {
    //Klasse som implementerer PersistenceI som er kommunikationen mellem persistence og domæne

    //Metode der returnerer facaden
    @Override
    public PersistenceFacade getFacade() {
        return PersistenceFacade.getInstance();
    }
}
