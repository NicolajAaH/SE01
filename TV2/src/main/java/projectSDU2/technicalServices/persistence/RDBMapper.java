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
    protected final Object getObjectFromStorage(int oid) {
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
        try {
            ResultSet resultSet = getResultSetAll();
            return getObjectsFromRecord(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private ResultSet getResultSetAll() throws SQLException {
        PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " +tableName+ ";");
        return statement.executeQuery();
    }

    protected abstract ArrayList<Object> getObjectsFromRecord(ResultSet resultSet);
}
