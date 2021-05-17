package projectSDU2.technicalServices.persistence;

import projectSDU2.technicalServices.PersistenceHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class RDBMapper extends PersistenceMapper{
    private String tableName;

    public RDBMapper(String tableName){
        this.tableName = tableName;
    }

    @Override
    protected Object getObjectFromStorage(int oid) {
        try {
            ResultSet resultSet = getResultSet(oid);
            return getObjectFromRecord(oid, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    protected abstract Object getObjectFromRecord(int oid, ResultSet resultSet);

    private ResultSet getResultSet(int oid) throws SQLException {
        PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " +tableName+ " WHERE id = ?;");
        statement.setInt(1, oid);
        return statement.executeQuery();
    }

    @Override
    protected void putObjectToStorage(Object object) {
       putObject(object);
    }

    protected abstract void putObject(Object object);

    @Override
    protected ArrayList<Object> getAllFromStorage() {
        ResultSet resultSet = getResultSetAll();
        return getObjectsFromRecord(resultSet);
    }

    private ResultSet getResultSetAll() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " +tableName+ ";");
            return statement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    protected abstract ArrayList<Object> getObjectsFromRecord(ResultSet resultSet);

    @Override
    protected void deleteObjectFromStorage(int oid) {
        deleteObject(oid);
    }

    protected abstract void deleteObject(int oid);

    @Override
    protected void editObjectFromStorage(int oid, Object object) {
        editObject(oid, object);
    }

    protected abstract void editObject(int oid, Object object);

    @Override
    protected int getNextIntFromStorage() {
        return getNextSerial();
    }

    protected abstract int getNextSerial();
}
