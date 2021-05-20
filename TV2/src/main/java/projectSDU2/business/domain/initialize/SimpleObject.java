package projectSDU2.business.domain.initialize;

import java.io.Serializable;

public class SimpleObject implements Serializable {
    //Attributter
    private int id;
    private String type;
    private String name;

    //Constructor
    public SimpleObject(int id, String type, String name){
        this.id = id;
        this.type = type;
        this.name = name;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    //Overrided toString metode
    @Override
    public String toString() {
        return type + ": " + name;
    }
}
