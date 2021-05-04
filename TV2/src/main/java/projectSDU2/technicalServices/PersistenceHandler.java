package projectSDU2.technicalServices;

import projectSDU2.business.domain.initialize.CreditingSystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PersistenceHandler {

    private static PersistenceHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "tv2";
    private String username = "postgres";
    private String password = "pgadmin123";
    private Connection connection = null;

    static CreditingSystem creditingSystem = CreditingSystem.getInstance();

    private PersistenceHandler(){
        initializePostgresqlDatabase();
    }

    public static PersistenceHandler getInstance(){
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void readFile(String filePath){
        File file = new File(filePath);
        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                //creditingSystem.getAccounts().add(new SystemAdministratorLogin(array[0], array[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> getAccounts(){
        ArrayList<String[]> accounts = new ArrayList<>();
        File file = new File("src/main/resources/users.txt");
        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                accounts.add(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static boolean findAccount(String email, String password){
        File file = new File("src/main/resources/users.txt");
        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                if(array[0].equals(email)){
                    if(array[1].equals(password)){
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String findType(String email){
        File file = new File("src/main/resources/users.txt");
        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                if(array[0].equals(email)){
                    return array[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}
