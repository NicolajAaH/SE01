package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionMapper extends RDBMapper {

    public ProductionMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        //ArrayList<Roles> roles = new ArrayList<>();
        try {
            /*PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + "CreditRoles, Roles, Credit" +
                    " WHERE CreditID = ? AND RoleID = Roles.id AND CreditID = Credit.id;");
            statement.setInt(1, oid);
            ResultSet resultSetRoles = statement.executeQuery();
            while(resultSetRoles.next()){
                roles.add(Roles.valueOf(resultSetRoles.getString("role")));
            }
            Credit credit = new Credit(oid, resultSet.getObject("PERSONKOLONNE"), roles);*/
            resultSet.next();
            String company = resultSet.getString("company");
            String name = resultSet.getString("name");
            boolean sent = resultSet.getBoolean("sent");
            boolean status = resultSet.getBoolean("status");
            return new Production(oid, company, name, status, sent);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
