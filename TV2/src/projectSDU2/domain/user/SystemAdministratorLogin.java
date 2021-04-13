package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;

import java.util.ArrayList;

class SystemAdministratorLogin extends ProducerLogin implements SystemAdministratorRights{

    @Override
    public Producer createProducer(String email, String password, String name, int phone) {
        return null;
    }

    @Override
    public Production createProduction(int productionID, ArrayList<Credit> credits) {
        return null;
    }

    @Override
    public void deleteProduction(int productionID) {

    }

    @Override
    public void deleteProducer(String email) {

    }

    @Override
    public void mergePersons(String email1, String email2) {

    }
}
