package projectSDU2.technicalServices.persistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;
import projectSDU2.business.nextGenPersistence.CreditMapper;
import projectSDU2.business.nextGenPersistence.PersonMapper;
import projectSDU2.business.nextGenPersistence.ProductionMapper;
import projectSDU2.business.nextGenPersistence.RoleMapper;
import projectSDU2.technicalServices.PersistenceHandler;

import java.util.ArrayList;
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

    public void put(Object object, String persClass){
        IMapper mapper = imapperMap.get(persClass);
        mapper.put(object);
    }

    public static void main(String[] args) {
        //System.out.println(instance.get(1, "personmapper"));
        //System.out.println(instance.get(1, "productionmapper"));
        //System.out.println(instance.get(1, "rolemapper"));
        //System.out.println(instance.get(1, "creditmapper"));
        //instance.put(Roles.light, "rolemapper");
        //instance.put(new Person("ny2", 12332111, "nymail@", "nytpass", "participant"), "personmapper");
        //SLET ALLE IMPORTS EFTER!!
        /*Person person = new Person("ny2", 5,12332111, "nymail@", "nytpass", "participant");
        ArrayList<Credit> credits = new ArrayList<>();
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(Roles.light);
        credits.add(new Credit(person, roles));
        Production production = new Production(credits, "companynyy", "navnny");
        instance.put(production, "productionmapper");*/
        System.out.println(instance.get(13, "productionmapper"));
    }
}
