package projectSDU2.Interfaces;

import projectSDU2.domain.user.Account;

public interface DomainI {

    //tilføj hvad GUI skal kunne tilgå
    boolean authorize(String email, String password);

    String findType(String email);

    Account findAccount(String email);

    void runSetup();
}
