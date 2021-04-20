package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;

import java.util.ArrayList;

public interface SystemAdministratorRights {

    void createProducer(String email, String password, String name, int phone, int ID, String company, ArrayList<Production> productions);

    void deleteProduction(int productionID);

    void deleteProducer(String email);

    void mergePersons(String email1, String email2);

    void validateProduction(Production production, boolean state);

    void createProducerLogin(Producer producer);
}
