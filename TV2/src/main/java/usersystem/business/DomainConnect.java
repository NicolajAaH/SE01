package usersystem.business;

import projectSDU2.business.domain.initialize.SimpleObject;
import usersystem.Interfaces.DomainI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DomainConnect implements DomainI {

    private Client client = Client.getInstance();

    @Override
    public List<SimpleObject> getAll() {
        return client.getAll();
    }

    @Override
    public List<SimpleObject> search(String s) {
        return client.searchedObjects(s);
    }

    @Override
    public HashMap<String, ArrayList<String>> getSpecific(String type, int id) {
        return client.getSpecific(type, id);
    }

    @Override
    public SimpleObject castToSimpleObject(Object object) {
        return (SimpleObject) object;
    }
}
