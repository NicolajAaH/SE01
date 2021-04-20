package projectSDU2.domain.user;

public class ParticipantLogin extends Account implements ParticipantRights{

    private Participant participant;


    ParticipantLogin(){
        setPerson(participant);
    }

    public ParticipantLogin(String email, String password) {
        setAccountEmail(email);
        setAccountPassword(password);
        setPerson(participant);
    }

    ParticipantLogin(Participant participant){
        this.participant = participant;
        setPerson(participant);
        setAccountPassword("" + participant.getPhone());
        setAccountEmail(participant.getEmail());
    }

    public ParticipantLogin(Participant participant, String password){
        this.participant = participant;
        setPerson(participant);
        setAccountPassword(password);
        setAccountEmail(participant.getEmail());
    }


    @Override
    public void setName(String name) {
        participant.setName(name);
    }

    @Override
    public void setPhone(int phone) {
        participant.setPhone(phone);
    }

    @Override
    public void setEmail(String email) {
        participant.setEmail(email);
    }



}
