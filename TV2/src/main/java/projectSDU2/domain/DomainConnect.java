package projectSDU2.domain;

import projectSDU2.Interfaces.DomainI;
import projectSDU2.domain.initialize.CreditingSystem;
import projectSDU2.domain.user.Account;

public class DomainConnect implements DomainI {

    private CreditingSystem creditingSystem = CreditingSystem.getInstance();

    @Override
    public boolean authorize(String email, String password) {
        return creditingSystem.authorizeAccount(email, password);
    }

    @Override
    public String findType(String email) {
        return creditingSystem.findType(email);
    }

    @Override
    public Account findAccount(String email) {
        return creditingSystem.findAccount(email);
    }

    @Override
    public void runSetup() {
        creditingSystem.setup();
    }


}
