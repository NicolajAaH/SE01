package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.user.Person;

import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    }

