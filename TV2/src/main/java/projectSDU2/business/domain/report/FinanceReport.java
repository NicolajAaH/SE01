package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;
import java.util.HashMap;


public class FinanceReport extends Report {
    private static HashMap<Person, Integer> top10credits;
    private static HashMap<Roles, Integer> creditTypes;
    private static HashMap<String, HashMap<Roles, Integer>> producers;
    private static int index = 1;
    private static String filename = nameOfFile + index + ".txt";
    private static HashMap<Roles, Integer> frequencyMap;
    private static ArrayList<Credit> credits;
    private static ArrayList<Roles> roles;

    public void generateFinanceReport() {
        top10credits = new HashMap<>();
        creditTypes = new HashMap<>();
        producers = new HashMap<>();
    }

    public static void generateFilmProducersReport() {
        producers = new HashMap<>();
        ArrayList<String> producingCompanies = new ArrayList<>();

        //finder navnene på alle producenterne
        for (Production production : cs.getProductions()) {
            if (producingCompanies.contains(production.getCompany()) == false)
                producingCompanies.add(production.getCompany());
        }
        //finder alle rollerne for alle produktionerne for hver producent
        for (String companyName : producingCompanies) {
            frequencyMap = new HashMap<>();
            for (Production production : cs.getProductions()) {
                credits = new ArrayList<>();
                roles = new ArrayList<>();
                if (companyName.equalsIgnoreCase(production.getCompany())) {
                    getCreditsAndRolesFromProduction(production);
                }
                countFrequencyRoles(roles, frequencyMap);
            }
            producers.put(companyName, frequencyMap);
        }
        System.out.println(producers);
        writeToFile("Oversigt over producenter " + filename, producers);
    }

    public static void generateRolesOverview() {
        creditTypes = new HashMap<>();
        roles = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            credits = new ArrayList<>();
            getCreditsAndRolesFromProduction(production);
        }
        countFrequencyRoles(roles, creditTypes);
        writeToFile2("Oversigt over roller " + filename, creditTypes);
    }

    public static void generateTop10MostCredited(){
        
    }

    //tæller hvor mange instanser der er af roller i en liste
    private static void countFrequencyRoles(ArrayList<Roles> list, HashMap<Roles, Integer> map) {
        for (Roles r : list) {
            Integer count = map.get(r);
            if (count == null) {
                count = 0;
            }
            map.put(r, count + 1);
        }
    }


    //udtrækker alle krediteringer og roller for en given production
    private static void getCreditsAndRolesFromProduction(Production production) {
        credits.addAll(production.getCredits());
        for (Credit credit : credits) {
            roles.addAll(credit.getRoles());
        }
    }

    public static void main(String[] args) {
        generateFilmProducersReport();
        generateRolesOverview();
    }

}
