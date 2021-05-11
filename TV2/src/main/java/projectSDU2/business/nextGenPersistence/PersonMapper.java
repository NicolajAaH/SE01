package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.user.Person;

import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonMapper extends RDBMapper {
    public PersonMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try{
            resultSet.next();
            String name = resultSet.getString("name");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String type = resultSet.getString("type");
            return new Person(name, oid, phone, email, password, type);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    protected void putObject(Object object) {
        try {
            Person person = (Person) object;
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO person(name, phone, email, password, type) VALUES (?,?,?,?,?);");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getPhone());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPassword());
            statement.setString(5, person.getType());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> persons = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int oid = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                persons.add(new Person(name, oid, phone, email, password, type));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return persons;
    }

    @Override
    protected void deleteObject(int oid) {
        try {
            PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection().prepareStatement("DELETE FROM person WHERE id = ?");
            statement2.setInt(1, oid);
            statement2.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void editObject(int oid, Object object) {
        Person person = (Person) object;
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("UPDATE person SET name = ?, phone = ?, email = ?, password = ? WHERE id=?;");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getPhone());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPassword());
            statement.setInt(5, oid);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected int getNextSerial() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT nextval(pg_get_serial_sequence('person', 'id')) as new_id;");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("new_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}

