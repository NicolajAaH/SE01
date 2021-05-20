package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.user.Person;

import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonMapper extends RDBMapper {
    //Constructor
    public PersonMapper(String tableName) {
        super(tableName);
    }

    //Henter Person ud fra et id
    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try {
            resultSet.next(); //Tager det næste (første og sidste) person i resultsettet
            String name = resultSet.getString("name");
            int phone = resultSet.getInt("phone");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String type = resultSet.getString("type");
            return new Person(name, oid, phone, email, password, type); //Returnerer personen
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //Fejler
    }

    //Indsætter en person i databasen
    @Override
    protected void putObject(Object object) {
        try {
            Person person = (Person) object; //Caster til en person
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                    .prepareStatement("INSERT INTO person(name, phone, email, password, type) VALUES (?,?,?,?,?);");
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

    //Henter alle personer
    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> persons = new ArrayList<>();
        try {
            while (resultSet.next()) { //Imens der er flere
                int oid = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                persons.add(new Person(name, oid, phone, email, password, type)); //Tilføj personen til listen
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return persons; //Returnerer dem
    }

    //Sletter en person ud fra et id
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

    //Ændrer en person
    @Override
    protected void editObject(int oid, Object object) {
        Person person = (Person) object; //Caster til person
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                    .prepareStatement("UPDATE person SET name = ?, phone = ?, email = ?, password = ? WHERE id=?;");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getPhone());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPassword());
            statement.setInt(5, oid);
            statement.execute(); //opdaterer personen ud fra attributterne som hentes fra det givne person objekt, samt dens id
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Hent værdien af næste id i rækkefølgen
    @Override
    protected int getNextSerial() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                    .prepareStatement("SELECT nextval(pg_get_serial_sequence('person', 'id')) as new_id;"); //Næste i sekvensen af SERIAL i person for kolonnen id
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("new_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; //Fejler
    }
}
