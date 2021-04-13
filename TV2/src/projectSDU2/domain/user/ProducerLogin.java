package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.credit.Roles;
import projectSDU2.domain.initialize.CreditingSystem;
import projectSDU2.domain.initialize.Server;

import java.util.ArrayList;

class ProducerLogin extends ParticipantLogin implements ProducerRights{

    private Producer producer;

    ProducerLogin(){

    }

    ProducerLogin(Producer producer){
        this.producer = producer;
        setAccountPassword("" + producer.getPhone());
        setAccountEmail(producer.getEmail());
    }

    @Override
    public void setCreditPerson(Person person) {

    }

    @Override
    public void setCreditRoles(ArrayList<Roles> roles) {

    }

    @Override
    public void createCredit(int ID, Production production, Person person, ArrayList<Roles> roles) {
        production.addCredit(new Credit(ID, person, roles));
    }

    @Override
    public void createParticipant(int ID, String email, String name, int phone) {
        Server.getCreditingSystem().addPerson(new Participant(name, phone, ID, email));
    }

    @Override
    public void createParticipantLogin(Participant participant) {
        Server.getCreditingSystem().addAccount(new ParticipantLogin(participant));
    }

    @Override
    public void deleteParticipant(Participant participant) {
        Server.getCreditingSystem().removeAccount(participant.getEmail());
    }

    @Override
    public Participant findParticipant(String email) {
        return (Participant) Server.getCreditingSystem().findPerson(email);
    }


    @Override
    public void createProduction(int productionID, ArrayList<Credit> credits) {
        Server.getCreditingSystem().addProduction(new Production(productionID, credits));
    }
}
