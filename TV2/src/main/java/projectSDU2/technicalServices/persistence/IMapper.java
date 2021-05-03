package projectSDU2.technicalServices.persistence;

public interface IMapper {

    Object get(int oid);

    void put(int oid, Object object);
}
