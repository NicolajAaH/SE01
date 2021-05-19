package projectSDU2.business.domain.initialize;

import java.io.Serializable;

public class Command implements Serializable {
    //Attributter
    private Commands command;
    private String parameters;

    //Constructor
    public Command(Commands command, String paramters){
        this.command = command;
        this.parameters = paramters;
    }

    //Getters
    public Commands getCommand() {
        return command;
    }

    public String getParameters() {
        return parameters;
    }
}
