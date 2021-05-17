package usersystem.Interfaces;

import projectSDU2.business.domain.initialize.SimpleObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DomainI {

    List<SimpleObject> getAll();

    List<SimpleObject> search(String s);

    HashMap<String, ArrayList<String>> getSpecific(String type, int id);

    SimpleObject castToSimpleObject(Object object);
}
