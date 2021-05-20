package projectSDU2.technicalServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistenceHandler {

    //Attributter til opstilling af database
    private static PersistenceHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private static String databaseName = "tv2";
    private String username = "postgres";
    private String password = "pgadmin123"; //Ændrer password til eget password for server adgang
    private Connection connection = null;

    //Singleton pattern, privat constructor
    private PersistenceHandler() {
        initializePostgresqlDatabase();
    }

    //Returnerer singleton instansen, lazy instantiation
    public static PersistenceHandler getInstance() {
        if (instance == null) { //Hvis den ikke er oprettet, opret
            instance = new PersistenceHandler();
        }
        return instance; //Returnerer instansen
    }

    //Initialiserer databasen
    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver()); //Registrerer driveren
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password); //Forbinder
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err); //Fejl
        } finally {
            if (connection == null) System.exit(-1); //Hvis forbindelsen ikke oprettes, så luk programmet
        }
    }

    //Returnerer connection
    public Connection getConnection() {
        return connection;
    }

    //Setter til at indstille databasenavnet
    public static void setDatabaseName(String database) {
        databaseName = database;
    }
}
