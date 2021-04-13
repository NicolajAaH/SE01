package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.credit.Roles;

import java.util.ArrayList;

class ProducerLogin extends ParticipantLogin implements ProducerRights{

    @Override
    public void setCreditPerson(Person person) {

    }

    @Override
    public void setCreditRoles(ArrayList<Roles> roles) {

    }

    @Override
    public Credit createCredit(Production production, Person person, ArrayList<Roles> roles) {
        return null;
    }

    @Override
    public Participant createParticipant(String email, String password, String name, int phone) {
        return null;
    }

    @Override
    public void deleteParticipant(String email) {

    }
}
