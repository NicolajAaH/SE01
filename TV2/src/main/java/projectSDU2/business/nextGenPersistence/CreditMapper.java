package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.business.domain.user.Person;
import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.PersistenceFacade;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditMapper extends RDBMapper {
    private CreditingSystem creditingSystem = CreditingSystem.getInstance();
    public CreditMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        ArrayList<Roles> roles = new ArrayList<>();
        try {
            resultSet.next();
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + "CreditRoles, Roles" + " WHERE CreditID = ? AND RoleID = Roles.id;");
            statement.setInt(1, oid);
            ResultSet resultSetRoles = statement.executeQuery();
            while(resultSetRoles.next()){
                roles.add(Roles.valueOf(resultSetRoles.getString("role")));
            }
            Person person = creditingSystem.findPerson(resultSet.getInt("personID"));
            return new Credit(oid, person, roles);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
