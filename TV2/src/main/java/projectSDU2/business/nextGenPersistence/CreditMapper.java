package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;
import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.PersistenceFacade;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditMapper extends RDBMapper {
    //Constructor
    public CreditMapper(String tableName) {
        super(tableName);
    }

    //Henter object ud fra et id
    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        ArrayList<Roles> roles = new ArrayList<>();
        try {
            resultSet.next(); //henter næste i resultsettet
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM " + "CreditRoles, Roles" + " WHERE CreditID = ? AND RoleID = Roles.id;"); //Henter fra CreditRoles for at få roller
            statement.setInt(1, oid); //første spørgsmålstegn erstattet med oid
            ResultSet resultSetRoles = statement.executeQuery();
            while (resultSetRoles.next()) {
                roles.add(Roles.valueOf(resultSetRoles.getString("role"))); //tilføjer roller til listen
            }
            Person person = (Person) PersistenceFacade.getInstance().get(resultSet.getInt("personid"), "personmapper"); //henter personen til credit
            return new Credit(oid, person, roles); //returnerer credit
        } catch (SQLException throwables) {
        }
        return null;
    }

    @Override
    protected void putObject(Object object) {
        //Ikke relevant at lave, da credits bliver tilføjet sammen med en produktion. Se production. Skal dog overrides, derfor er den her
    }

    //Henter alle credits
    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> credits = new ArrayList<>();
        try {
            while (resultSet.next()) { //imens den har flere
                ArrayList<Roles> roles = new ArrayList<>();
                int oid = resultSet.getInt("id");
                PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                        .prepareStatement("SELECT * FROM " + "CreditRoles, Roles" + " WHERE CreditID = ? AND RoleID = Roles.id;");
                statement.setInt(1, oid);
                ResultSet resultSetRoles = statement.executeQuery();
                while (resultSetRoles.next()) {
                    roles.add(Roles.valueOf(resultSetRoles.getString("role")));
                }
                Person person = (Person) PersistenceFacade.getInstance().get(resultSet.getInt("personid"), "personmapper"); //Samme som getObject
                credits.add(new Credit(oid, person, roles));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credits;
    }

    //Sletter en credit ud fra et id
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

    //Ændrer en credit
    @Override
    protected void editObject(int oid, Object object) {
        Credit credit = (Credit) object;
        try {
            if (PersistenceFacade.getInstance().get(credit.getCreditID(), "creditmapper") == null) { //Hvis den ikke er oprettet endnu, så opret
                PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection()
                        .prepareStatement("INSERT INTO credit(productionID, personID) VALUES (?,?);");
                statement2.setInt(1, oid);
                statement2.setInt(2, credit.getPerson().getId());
                statement2.execute();

                PreparedStatement statementForIDCredit = PersistenceHandler.getInstance().getConnection()
                        .prepareStatement("SELECT * FROM public.credit ORDER BY id ASC;");
                ResultSet resultSetIDCredit = statementForIDCredit.executeQuery();
                int resultId = 0;
                while (resultSetIDCredit.next()) {
                    resultId = resultSetIDCredit.getInt("id");
                }//Få ID fra credit

                for (Roles role : credit.getRoles()) { //Få roller fra credit
                    PreparedStatement preparedStatement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM Roles WHERE role = ?;");
                    preparedStatement.setString(1, role.name());
                    ResultSet resultSet = preparedStatement.executeQuery(); //Finder id for en rolle
                    resultSet.next();
                    PreparedStatement statement3 = PersistenceHandler.getInstance().getConnection()
                            .prepareStatement("INSERT INTO creditroles(roleID, creditID) VALUES (?,?);");
                    statement3.setInt(1, resultSet.getInt("id"));
                    statement3.setInt(2, resultId);
                    statement3.execute(); //indsætter det i creditroles for at binde en credit sammen med en rolle
                }
            } else { //Credit er i systemet
                PreparedStatement statement = PersistenceHandler.getInstance().getConnection()
                        .prepareStatement("UPDATE credit SET productionid = ?, personid = ? WHERE id=?;");
                statement.setInt(1, oid);
                statement.setInt(2, credit.getPerson().getId());
                statement.setInt(3, credit.getCreditID());
                statement.execute(); //Opdaterer credit ud fra den credit der er givet med

                PreparedStatement statement2 = PersistenceHandler.getInstance().getConnection().prepareStatement("DELETE FROM creditroles WHERE creditid = ?;");
                statement2.setInt(1, credit.getCreditID());
                statement2.execute(); //sletter roller fra crediten der lige er blevet opdateret

                for (Roles role : credit.getRoles()) {
                    PreparedStatement preparedStatement = PersistenceHandler.getInstance().getConnection().prepareStatement("SELECT id FROM Roles WHERE role = ?;");
                    preparedStatement.setString(1, role.name());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next(); //finder id af rolle
                    PreparedStatement statement3 = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO creditroles(roleid, creditid) VALUES (?,?);");
                    statement3.setInt(1, resultSet.getInt("id"));
                    statement3.setInt(2, credit.getCreditID());
                    statement3.execute(); //indsætter de opdaterede roller fra crediten der er blevet opdateret
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Henter værdien af næste id i rækken
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
        return -1; //Hvis det fejler
    }
}