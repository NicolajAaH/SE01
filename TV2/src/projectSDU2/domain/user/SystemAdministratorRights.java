package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;

import java.util.ArrayList;

public interface SystemAdministratorRights {

    Producer createProducer(String email, String password, String name, int phone);

    Production createProduction(int productionID, ArrayList<Credit> credits);

    void deleteProduction(int productionID);

    void deleteProducer(String email);

    void mergePersons(String email1, String email2);
}
