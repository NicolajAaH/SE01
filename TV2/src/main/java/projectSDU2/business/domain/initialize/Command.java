package projectSDU2.business.domain.initialize;

import java.io.Serializable;

public class Command implements Serializable {
    private Commands command;
    private String parameters;

    public Command(Commands command, String paramters){
        this.command = command;
        this.parameters = paramters;
    }

    public Commands getCommand() {
        return command;
    }

    public String getParameters() {
        return parameters;
    }
}
