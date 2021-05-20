package projectSDU2.Interfaces;

import projectSDU2.technicalServices.persistence.PersistenceFacade;

public interface PersistenceI {
    //Hvilke ting businesslaget (domæne) skal kunne tilgå i persistenslaget

    //Returnerer instansen af facaden til databasen
    PersistenceFacade getFacade();
}
