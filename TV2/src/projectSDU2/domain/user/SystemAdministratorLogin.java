package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.initialize.CreditingSystem;
import projectSDU2.domain.initialize.Server;

import java.util.ArrayList;

class SystemAdministratorLogin extends ProducerLogin implements SystemAdministratorRights{

    SystemAdministratorLogin(String email, String password) {
        setAccountEmail(email);
        setAccountPassword(password);
    }


    @Override
    public void createProducer(String email, String password, String name, int phone, int ID, String company, ArrayList<Production> productions) {
        Server.getCreditingSystem().addPerson(new Producer(name, phone, ID, email, company, productions));
    }

    @Override
    public void deleteProduction(int productionID) {
        Server.getCreditingSystem().removeProduction(productionID);
    }

    @Override
    public void deleteProducer(String email) {
        Server.getCreditingSystem().removeAccount(email);
    }

    @Override
    public void mergePersons(String email1, String email2) {
        //TODO
    }

    @Override
    public void validateCredit(boolean status) {
        if(status){
            //Server.getCreditingSystem().getProductions().
        }
        else{
            //AFVIS
        }
    }

    @Override
    public void createProducerLogin(Producer producer) {
        Server.getCreditingSystem().addAccount(new ProducerLogin(producer));
    }


}
