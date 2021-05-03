package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Roles;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
