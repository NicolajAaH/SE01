package projectSDU2.technicalServices.persistence;

public abstract class PersistenceMapper implements IMapper{

    @Override
    public Object get(int oid) {
        return getObjectFromStorage(oid);
    }

    protected Object getObjectFromStorage(int oid) {
        return null;
    }

    @Override
    public void put(Object object) {
        putObjectToStorage(object);
    }

    protected void putObjectToStorage(Object object) {

    }
}
