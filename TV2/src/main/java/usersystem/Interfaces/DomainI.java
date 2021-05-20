package usersystem.Interfaces;

import projectSDU2.business.domain.initialize.SimpleObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DomainI {
    //Interface som er kommunikation mellem domæne og presentation.
    //Forklaring af metoder er skrevet som kommentarer i DomainConnect

    List<SimpleObject> getAll();

    List<SimpleObject> search(String s);

    HashMap<String, ArrayList<String>> getSpecific(String type, int id);

    SimpleObject castToSimpleObject(Object object);
}
