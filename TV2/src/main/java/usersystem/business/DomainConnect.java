package usersystem.business;

import projectSDU2.business.domain.initialize.SimpleObject;
import usersystem.Interfaces.DomainI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DomainConnect implements DomainI {
    //Forbindelsen mellem presentation og domæne i forbrugersystemet

    //Henter client instansen og gemmer den i en variabel
    private Client client = Client.getInstance();

    //Returnerer alle personer (undtagen systemadmins) og produktioner
    @Override
    public List<SimpleObject> getAll() {
        return client.getAll();
    }

    //Søger efter personer/produktioner ud fra en streng
    @Override
    public List<SimpleObject> search(String s) {
        return client.searchedObjects(s);
    }

    //Henter specifik ud fra type og id, returnerer HashMap med attributter
    @Override
    public HashMap<String, ArrayList<String>> getSpecific(String type, int id) {
        return client.getSpecific(type, id);
    }

    //Caster til SimpleObject og returnerer det, så presentation overholder lagdelingen
    @Override
    public SimpleObject castToSimpleObject(Object object) {
        return (SimpleObject) object;
    }
}
