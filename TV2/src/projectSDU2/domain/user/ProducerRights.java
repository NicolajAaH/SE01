package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.credit.Roles;

import java.util.ArrayList;

public interface ProducerRights {

    void setCreditPerson(Person person);

    void setCreditRoles(ArrayList<Roles> roles);

    Credit createCredit(Production production, Person person, ArrayList<Roles> roles);

    Participant createParticipant(String email, String password, String name, int phone);

    void deleteParticipant(String email);


}
