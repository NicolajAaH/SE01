package projectSDU2.technicalServices.persistence;

import projectSDU2.business.nextGenPersistence.CreditMapper;
import projectSDU2.business.nextGenPersistence.PersonMapper;
import projectSDU2.business.nextGenPersistence.ProductionMapper;
import projectSDU2.business.nextGenPersistence.RoleMapper;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceFacade {
    //Attributter, singleton pattern, så der kun er en persistencefacade
    private static PersistenceFacade instance = new PersistenceFacade();
    private HashMap<String, IMapper> imapperMap = new HashMap<>();

    //Privat constructor
    private PersistenceFacade() { //Tilføjer de fire mappere i et HashMap
        imapperMap.put("creditmapper", new CreditMapper("credit"));
        imapperMap.put("personmapper", new PersonMapper("person"));
        imapperMap.put("rolemapper", new RoleMapper("roles"));
        imapperMap.put("productionmapper", new ProductionMapper("production"));
    }

    //Returnerer singleton instansen af facaden
    public static PersistenceFacade getInstance() {
        return instance;
    }

    //Henter et objekt ud fra et id og mapperklasse
    public Object get(int oid, String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        return mapper.get(oid);
    }

    //Henter alle objekter af en mapperklasse
    public ArrayList<Object> getAll(String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        return mapper.getAll();
    }

    //Indsætter et objekt af en mapperklasse
    public void put(Object object, String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        mapper.put(object);
    }

    //Sletter et objekt ud fra et id af en mapperklasse
    public void delete(int oid, String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        mapper.delete(oid);
    }

    //Ændrer et objekt af en mapperklasse
    public void edit(int oid, Object object, String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        mapper.edit(oid, object);
    }

    //Kan bruges til at hente værdien af næste SERIAL af en mapperklasse fra databasen
    public int getNextInt(String persClass) {
        IMapper mapper = imapperMap.get(persClass);
        return mapper.getNextInt();
    }
}
