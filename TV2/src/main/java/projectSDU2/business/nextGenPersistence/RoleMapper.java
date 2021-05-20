package projectSDU2.business.nextGenPersistence;

import projectSDU2.business.domain.credit.Roles;
import projectSDU2.technicalServices.PersistenceHandler;
import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleMapper extends RDBMapper {
    //Constructor
    public RoleMapper(String tableName) {
        super(tableName);
    }

    //Henter role objekt ud fra et id
    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try {
            resultSet.next(); //Næste værdi og eneste i resultset
            Roles role = Roles.valueOf((String) resultSet.getObject("role")); //Konverterer den til en role
            return role;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //Rolle ikke fundet
    }

    //Indsæt en role
    @Override
    protected void putObject(Object object) {
        try {
            Roles role = (Roles) object; //Caster til roles
            PreparedStatement statement = PersistenceHandler.getInstance().getConnection().prepareStatement("INSERT INTO roles(role) VALUES (?);");
            statement.setString(1, role.name());
            statement.executeQuery(); //Indsætter en role
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Henter alle roles
    @Override
    protected ArrayList<Object> getObjectsFromRecord(ResultSet resultSet) {
        ArrayList<Object> roles = new ArrayList<>();
        try {
            while (resultSet.next()) { //For hver role i resultSet
                Roles role = Roles.valueOf((String) resultSet.getObject("role")); //Cast den til role
                roles.add(role); //Tilføj den til listen
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roles; //Returnerer listen
    }

    @Override
    protected void deleteObject(int oid) {
        //Det giver ikke mening at slette en role, og dermed er den ikke implementeret.
        //Sletning af en rolle ville skabe problemer i forhold til krediteringer og produktioner, og derfor ikke implementeret. Den skal dog overrides, derfor er den her
    }

    @Override
    protected void editObject(int oid, Object object) {
        //Det giver ikke mening at ændre en role, og dermed er den ikke implementeret.
        //Ændring af en rolle ville skabe problemer i forhold til krediteringer og produktioner, og derfor ikke implementeret. Den skal dog overrides, derfor er den her
    }

    //Next serial er ubrugelig i forhold til role, da næste id ikke skal vises nogen steder. Derfor returneres der -1 for at vise at den er ugyldig.
    @Override
    protected int getNextSerial() {
        return -1;
    }
}
