package projectSDU2.data;

import projectSDU2.domain.initialize.CreditingSystem;
import projectSDU2.domain.user.SystemAdministratorLogin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class PersistenceHandler {

    static CreditingSystem creditingSystem = CreditingSystem.getInstance();

    public static void readFile(String filePath){
        File file = new File(filePath);
        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            scanner.nextLine();
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] array = line.split(",");
                creditingSystem.getAccounts().add(new SystemAdministratorLogin(array[0], array[1]));
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
