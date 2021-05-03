package projectSDU2.domain.initialize;

public class Server {
    private static CreditingSystem creditingSystem = CreditingSystem.getInstance();

    void start(){
        throw new UnsupportedOperationException("Not yet supported.");
    }

    public static CreditingSystem getCreditingSystem() {
        return creditingSystem;
    }
}
