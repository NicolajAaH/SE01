package projectSDU2.technicalServices.persistence;

import projectSDU2.technicalServices.PersistenceHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class RDBMapper extends PersistenceMapper {
    //Abstrakt klasse som arver fra persistencemapper

    //Attribut
    private String tableName;

    //Constructor
    public RDBMapper(String tableName) {
        this.tableName = tableName;
    }

    //Metode til at få resultset af en klasse der henter alt i et table
    private ResultSet getResultSet(int oid) throws SQLException {
        PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?;");
        statement.setInt(1, oid);
        return statement.executeQuery();
    }

    //Overrided metode fra PersistenceMapper til at hente objekt ud fra id
    @Override
    protected Object getObjectFromStorage(int oid) {
        try {
            ResultSet resultSet = getResultSet(oid);
            return getObjectFromRecord(oid, resultSet); //Kalder abstrakt metode, som overrides i de enkelte med id og resultset
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //Fejl
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract Object getObjectFromRecord(int oid, ResultSet resultSet);

    //Overrided metode fra PersistenceMapper til at indsætte objekt
    @Override
    protected void putObjectToStorage(Object object) {
        putObject(object); //Kalder abstrakt metode, som overrides i de enkelte med objekt
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract void putObject(Object object);

    //Metode som returnerer et resultset med alle objekter af en type
    private ResultSet getResultSetAll() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + tableName + ";");
            return statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //Fejl
    }

    //Henter alle objekter af en klasse
    @Override
    protected ArrayList<Object> getAllFromStorage() {
        ResultSet resultSet = getResultSetAll(); //Kalder anden metode
        return getObjectsFromRecord(resultSet); //Kalder abstrakt metode, som overrides i de enkelte med resultSet
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract ArrayList<Object> getObjectsFromRecord(ResultSet resultSet);

    //Sletter et objekt ud fra et id af en klasse
    @Override
    protected void deleteObjectFromStorage(int oid) {
        deleteObject(oid); //Kalder abstrakt metode, som overrides i de enkelte med oid
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract void deleteObject(int oid);

    //Ændrer et objekt af en klasse ud fra et id og objekt
    @Override
    protected void editObjectFromStorage(int oid, Object object) {
        editObject(oid, object); //Kalder abstrakt metode, som overrides i de enkelte med id og object
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract void editObject(int oid, Object object);

    //Henter værdien af næste SERIAL for en klasse (id)
    @Override
    protected int getNextIntFromStorage() {
        return getNextSerial(); //Kalder abstrakt metode, som overrides i de enkelte
    }

    //Abstrakt metode som skal overrides i de konkrete mappere
    protected abstract int getNextSerial();
}
