package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Roles;
import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleMapper extends RDBMapper {
    public RoleMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try{
            resultSet.next();
            Roles role = Roles.valueOf((String) resultSet.getObject("role"));
            return role;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //Rolle ikke fundet
    }

    @Override
    protected void putObject(Object object) {
        try {
            Roles role = (Roles) object;
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO roles(role) VALUES (?);");
            statement.setString(1, role.name());
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> roles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Roles role = Roles.valueOf((String) resultSet.getObject("role"));
                roles.add(role);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roles;
    }

    @Override
    protected void deleteObject(int oid) {
        //giver det mening at slette en role???
    }

    @Override
    protected void editObject(int oid, Object object) {
        //giver det mening at ændre en role?
    }

    @Override
    protected int getNextSerial() {
        return -1;
    }
}
