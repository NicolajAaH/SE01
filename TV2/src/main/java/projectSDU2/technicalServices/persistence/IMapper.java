package projectSDU2.technicalServices.persistence;

import java.util.ArrayList;

public interface IMapper {
    //Interface der bruges i persistens, se kommentarer i PersistenceMapper

    Object get(int oid);

    void put(Object object);

    ArrayList<Object> getAll();

    void delete(int oid);

    void edit(int oid, Object object);

    int getNextInt();
}
