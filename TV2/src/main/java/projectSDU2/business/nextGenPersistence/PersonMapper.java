package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.user.Participant;
import projectSDU2.business.domain.user.Person;
import projectSDU2.business.domain.user.Producer;
import projectSDU2.business.domain.user.Systemadministrator;
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
            if(type.equals("systemadministrator")){
                return new Systemadministrator(oid, name, phone, email, password);
            }
            else if(type.equals("producer")){
                String company = resultSet.getString("company");
                return new Producer(name, phone, oid, email, company); //mangler productions
            }else if(type.equals("participant")){
                return new Participant(oid, name, phone, email, password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
