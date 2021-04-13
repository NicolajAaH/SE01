package projectSDU2.domain.user;

class ParticipantLogin extends Account implements ParticipantRights{

    private Participant participant;

    ParticipantLogin(){

    }

    ParticipantLogin(Participant participant){
        this.participant = participant;
        setAccountPassword("" + participant.getPhone());
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
