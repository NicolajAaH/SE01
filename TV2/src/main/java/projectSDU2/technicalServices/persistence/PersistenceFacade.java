package projectSDU2.technicalServices.persistence;

import projectSDU2.business.nextGenPersistence.CreditMapper;
import projectSDU2.business.nextGenPersistence.PersonMapper;
import projectSDU2.business.nextGenPersistence.ProductionMapper;
import projectSDU2.business.nextGenPersistence.RoleMapper;
import projectSDU2.technicalServices.PersistenceHandler;

import java.util.HashMap;

public class PersistenceFacade {
    private static PersistenceFacade instance = new PersistenceFacade();
    HashMap<String, IMapper> imapperMap = new HashMap<>();

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

    public static void main(String[] args) {
        System.out.println(instance.get(1, "personmapper"));
        System.out.println(instance.get(1, "productionmapper"));
        System.out.println(instance.get(1, "rolemapper"));
        System.out.println(instance.get(1, "creditmapper"));
    }
}
