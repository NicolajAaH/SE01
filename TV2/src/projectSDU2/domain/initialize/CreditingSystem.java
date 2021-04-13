package projectSDU2.domain.initialize;

import projectSDU2.domain.credit.Production;
import projectSDU2.domain.user.Account;
import projectSDU2.domain.user.Person;

import java.util.ArrayList;

public class CreditingSystem {
    private ArrayList<Production> productions;
    private ArrayList<Account> accounts;
    private ArrayList<Person> persons;

    public static void main(String[] args) {
        //
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void addProduction(Production production){
        productions.add(production);
    }

    public Account findAccount(String email){
        for (Account account : accounts){
            if(account.getEmail().equals(email)){
                return account;
            }
        }
        return null;
    }

    public Person findPerson(String email){
        for (Person person : persons){
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        return null;
    }

    public void removeAccount(String email){
        accounts.remove(findAccount(email));
    }

    public Production findProduction(int productionID){
        for (Production production : productions){
            if(production.getProductionID() == productionID){
                return production;
            }
        }
        return null;
    }

    public void removeProduction(int productionID){
        productions.remove(findProduction(productionID));
    }
}
