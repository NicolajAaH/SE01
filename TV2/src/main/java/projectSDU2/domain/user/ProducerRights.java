package projectSDU2.domain.user;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.credit.Production;
import projectSDU2.domain.credit.Roles;

import java.util.ArrayList;

public interface ProducerRights {

    void setCreditPerson(Person person);

    void setCreditRoles(ArrayList<Roles> roles);

    void createCredit(int ID, Production production, Person person, ArrayList<Roles> roles);

    void createParticipant(int ID, String email, String name, int phone);

    void createParticipantLogin(Participant participant);

    void deleteParticipant(Participant participant);

    Participant findParticipant(String email);

    void createProduction(int productionID, ArrayList<Credit> credits);


}
