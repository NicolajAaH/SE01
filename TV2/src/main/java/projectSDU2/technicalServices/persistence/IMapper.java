package projectSDU2.technicalServices.persistence;

import java.util.ArrayList;

public interface IMapper {

    Object get(int oid);

    void put(Object object);

    ArrayList<Object> getAll();
}
