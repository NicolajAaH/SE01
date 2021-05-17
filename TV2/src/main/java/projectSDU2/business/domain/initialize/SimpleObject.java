package projectSDU2.business.domain.initialize;

import java.io.Serializable;

public class SimpleObject implements Serializable {
    private int id;
    private String type;
    private String name;

    public SimpleObject(int id, String type, String name){
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type + ": " + name;
    }
}
