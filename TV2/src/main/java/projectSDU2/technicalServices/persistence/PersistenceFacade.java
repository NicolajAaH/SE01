package projectSDU2.technicalServices.persistence;

import projectSDU2.business.nextGenPersistence.CreditMapper;
import projectSDU2.business.nextGenPersistence.PersonMapper;
import projectSDU2.business.nextGenPersistence.ProductionMapper;
import projectSDU2.business.nextGenPersistence.RoleMapper;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceFacade {
    private static PersistenceFacade instance = new PersistenceFacade();
    private HashMap<String, IMapper> imapperMap = new HashMap<>();

    private PersistenceFacade(){
        imapperMap.put("creditmapper", new CreditMapper("credit"));
        imapperMap.put("personmapper", new PersonMapper("person"));
        imapperMap.put("rolemapper", new RoleMapper("roles"));
        imapperMap.put("productionmapper", new ProductionMapper("production"));
    }

    public static PersistenceFacade getInstance(){
        return instance;
    }

    public Object get(int oid, String persClass){
        IMapper mapper = imapperMap.get(persClass);
        return mapper.get(oid);
    }

    public ArrayList<Object> getAll(String persClass){
        IMapper mapper = imapperMap.get(persClass);
        return mapper.getAll();
    }

    public void put(Object object, String persClass){
        IMapper mapper = imapperMap.get(persClass);
        mapper.put(object);
    }

    public void delete(int oid, String persClass){
        IMapper mapper = imapperMap.get(persClass);
        mapper.delete(oid);
    }

    public void edit(int oid, Object object, String persClass){
        IMapper mapper = imapperMap.get(persClass);
        mapper.edit(oid, object);
    }

    public int getNextInt(String persClass){
        IMapper mapper = imapperMap.get(persClass);
        return mapper.getNextInt();
    }
}
