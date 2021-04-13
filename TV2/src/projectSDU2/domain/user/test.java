package projectSDU2.domain.user;

public class test {

    public static void main(String[] args) {
        Participant participant = new Participant("test", 12345678, 1, "test@test.dk");
        ParticipantLogin participantLogin = new ParticipantLogin(participant);

        System.out.println(participantLogin.getEmail() + "\t" + participantLogin.getPassword() + "\t" + participant.getName() + "\t" + participant.getPhone() + "\t" + participant.getId());

    }
}
