package projectSDU2.domain.user;


public class Participant extends Person {
    private ParticipantLogin participantLogin = new ParticipantLogin(this);

    Participant(String name, int phone, int ID, String email){
        this.name = name;
        this.phone = phone;
        this.id = ID;
        this.email = email;
    }

}
