package projectSDU2.technicalServices.persistence;

import java.util.ArrayList;

public abstract class PersistenceMapper implements IMapper {

    //Overrider get metoden og kalder en anden metode
    @Override
    public Object get(int oid) {
        return getObjectFromStorage(oid);
    }

    //Metode som overrides i nedarvende klasser
    protected Object getObjectFromStorage(int oid) {
        return null; //Da den skal overrides er returværdien irrelevant
    }

    //Overrider put metoden og kalder en anden metode
    @Override
    public void put(Object object) {
        putObjectToStorage(object);
    }

    //Metode som overrides i nedarvende klasser
    protected void putObjectToStorage(Object object) {
        //intet logik da den skal overrides
    }

    //Overrider getAll metoden og kalder en anden metode
    @Override
    public ArrayList<Object> getAll() {
        return getAllFromStorage();
    }

    //Metode som overrides i nedarvende klasser
    protected ArrayList<Object> getAllFromStorage() {
        return null; //Da den skal overrides er returværdien irrelevant
    }

    //Overrider delete metoden og kalder en anden metode
    @Override
    public void delete(int oid) {
        deleteObjectFromStorage(oid);
    }

    //Metode som overrides i nedarvende klasser
    protected void deleteObjectFromStorage(int oid) {
        //intet logik da den skal overrides
    }

    //Overrider edit metoden og kalder en anden metode
    @Override
    public void edit(int oid, Object object) {
        editObjectFromStorage(oid, object);
    }

    //Metode som overrides i nedarvende klasser
    protected void editObjectFromStorage(int oid, Object object) {
        //intet logik da den skal overrides
    }

    //Overrider getNextInt metoden og kalder en anden metode
    @Override
    public int getNextInt() {
        return getNextIntFromStorage();
    }

    //Metode som overrides i nedarvende klasser
    protected int getNextIntFromStorage() {
        return -1; //Da den skal overrides er returværdien irrelevant
    }
}
