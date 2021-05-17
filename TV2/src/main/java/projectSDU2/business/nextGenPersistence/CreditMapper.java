package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
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
            while (resultSetRoles.next()) {
                roles.add(Roles.valueOf(resultSetRoles.getString("role")));
            }
            Person person = (Person) PersistenceFacade.getInstance().get(resultSet.getInt("personid"), "personmapper");
            return new Credit(oid, person, roles);
        } catch (SQLException throwables) {
        }
        return null;
    }//tjek redigering

    @Override
    protected void putObject(Object object) {

    }

    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> credits = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ArrayList<Roles> roles = new ArrayList<>();
                int oid = resultSet.getInt("id");
                PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + "CreditRoles, Roles" + " WHERE CreditID = ? AND RoleID = Roles.id;");
                statement.setInt(1, oid);
                ResultSet resultSetRoles = statement.executeQuery();
                while (resultSetRoles.next()) {
                    roles.add(Roles.valueOf(resultSetRoles.getString("role")));
                }
                Person person = (Person) PersistenceFacade.getInstance().get(resultSet.getInt("personid"), "personmapper");
                credits.add(new Credit(oid, person, roles));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credits;
    }

    @Override
    protected void deleteObject(int oid) {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("DELETE FROM Credit WHERE id = ?");
            statement.setInt(1, oid);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void editObject(int oid, Object object) {
        Credit credit = (Credit) object;
        try {
            if(PersistenceFacade.getInstance().get(credit.getCreditID(), "creditmapper") == null){
                System.out.println(credit.toString() + "I NULL");
                PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO credit(productionID, personID) VALUES (?,?);");
                statement2.setInt(1, oid);
                statement2.setInt(2, credit.getPerson().getId());
                statement2.execute();

                PreparedStatement statementForIDCredit = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM public.credit ORDER BY id ASC;");
                ResultSet resultSetIDCredit = statementForIDCredit.executeQuery();
                int resultId = 0;
                while(resultSetIDCredit.next()){
                    resultId = resultSetIDCredit.getInt("id");
                }
                //FÅ ID fra credit
                for (Roles role : credit.getRoles()) {
                    PreparedStatement preparedStatement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM Roles WHERE role = ?;");
                    preparedStatement.setString(1, role.name());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    PreparedStatement statement3 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO creditroles(roleID, creditID) VALUES (?,?);");
                    statement3.setInt(1, resultSet.getInt("id"));
                    statement3.setInt(2, resultId);
                    statement3.execute();
                }
            }else {
                System.out.println(credit.toString() + "I ELSE");
                PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("UPDATE credit SET productionid = ?, personid = ? WHERE id=?;");
                statement.setInt(1, oid);
                statement.setInt(2, credit.getPerson().getId());
                statement.setInt(3, credit.getCreditID());
                statement.execute();

                PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection().prepareStatement("DELETE FROM creditroles WHERE creditid = ?;");
                statement2.setInt(1, credit.getCreditID());
                statement2.execute();

                for (Roles role : credit.getRoles()) {
                    PreparedStatement preparedStatement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM Roles WHERE role = ?;");
                    preparedStatement.setString(1, role.name());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    PreparedStatement statement3 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO creditroles(roleid, creditid) VALUES (?,?);");
                    statement3.setInt(1, resultSet.getInt("id"));
                    statement3.setInt(2, credit.getCreditID());
                    statement3.execute();
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected int getNextSerial() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT nextval(pg_get_serial_sequence('credit', 'id')) as new_id;");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("new_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}