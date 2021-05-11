package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.PersistenceFacade;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductionMapper extends RDBMapper {

    public ProductionMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try {
            ArrayList<Credit> credits = new ArrayList<>();
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + "credit" + " WHERE productionID = ?;");
            statement.setInt(1, oid);
            ResultSet resultSetCredits = statement.executeQuery();
            while(resultSetCredits.next()){
                credits.add((Credit) PersistenceFacade.getInstance().get(resultSetCredits.getInt("id"), "creditmapper"));
            }
            resultSet.next();
            String company = resultSet.getString("company");
            String name = resultSet.getString("name");
            boolean sent = resultSet.getBoolean("sent");
            boolean status = resultSet.getBoolean("status");
            return new Production(oid, company, name, status, sent, credits);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    protected void putObject(Object object) {
        try {
            Production production = (Production) object;
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO production(status, sent, company, name) VALUES (?,?,?,?);");
            statement.setBoolean(1, false);
            statement.setBoolean(2, false);
            statement.setString(3, production.getCompany());
            statement.setString(4, production.getName());
            statement.execute();

            PreparedStatement statementForID = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM production;");
            ResultSet resultSetID = statementForID.executeQuery();
            int id = -1;
            while(resultSetID.next()){
                id = resultSetID.getInt("id");
            }//FÅ ID fra production

            for (Credit credit : production.getCredits()){
                PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO credit(productionID, personID) VALUES (?,?);");
                statement2.setInt(1, id);
                statement2.setInt(2, credit.getPerson().getId());
                statement2.execute();

                PreparedStatement statementForIDCredit = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM credit;");
                ResultSet resultSetIDCredit = statementForIDCredit.executeQuery();
                int resultId = -1;
                while(resultSetIDCredit.next()){
                    resultId = resultSetIDCredit.getInt("id");
                }//FÅ ID fra credit
                for(Roles role : credit.getRoles()) {
                    PreparedStatement preparedStatement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM Roles WHERE role = ?;");
                    preparedStatement.setString(1, role.name());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    PreparedStatement statement3 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO creditroles(roleID, creditID) VALUES (?,?);");
                    statement3.setInt(1, resultSet.getInt("id"));
                    statement3.setInt(2, resultId);
                    statement3.execute();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> productions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int oid = resultSet.getInt("id");
                ArrayList<Credit> credits = new ArrayList<>();
                PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT * FROM " + "credit" + " WHERE productionID = ?;");
                statement.setInt(1, oid);
                ResultSet resultSetCredits = statement.executeQuery();
                while(resultSetCredits.next()){
                    credits.add((Credit) PersistenceFacade.getInstance().get(resultSetCredits.getInt("id"), "creditmapper"));
                }
                String company = resultSet.getString("company");
                String name = resultSet.getString("name");
                boolean sent = resultSet.getBoolean("sent");
                boolean status = resultSet.getBoolean("status");
                productions.add(new Production(oid, company, name, status, sent, credits));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productions;
    }

    @Override
    protected void deleteObject(int oid) {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("DELETE FROM production WHERE id = ?");
            statement.setInt(1, oid);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void editObject(int oid, Object object) {
        Production production = (Production) object;
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("UPDATE production SET status = ?, sent = ?, company = ?, name = ? WHERE id=?;");
            statement.setBoolean(1, production.isStatus());
            statement.setBoolean(2, production.isSent());
            statement.setString(3, production.getCompany());
            statement.setString(4, production.getName());
            statement.setInt(5, oid);
            statement.execute();
            for (Credit credit : production.getCredits()){
                PersistenceFacade.getInstance().edit(oid, credit, "creditmapper");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected int getNextSerial() {
        try {
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT nextval(pg_get_serial_sequence('production', 'id')) as new_id;");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("new_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
