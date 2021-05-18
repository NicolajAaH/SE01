package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;
import java.util.HashMap;


public class FinanceReport extends Report {
    private HashMap<Person, Integer> top10credits;
    private HashMap<Roles, Integer> creditTypes;
    private static HashMap<String, HashMap<Roles, Integer>> producers = new HashMap<>();
    private static int index = 1;
    private static String filename = "Finansrapport " + nameOfFile + index;

    public void generateFinanceReport() {
        top10credits = new HashMap<>();
        creditTypes = new HashMap<>();
        producers = new HashMap<>();

    }

    public void generateCompaniesAndTheirCreditsReport(String companyName) {
        ArrayList<Credit> credits = new ArrayList<>();
        ArrayList<Roles> roles = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            if (production.getCompany().compareToIgnoreCase(companyName) == 1) {
                credits.addAll(production.getCredits());
                for (Credit credit : credits) {
                    roles.addAll(credit.getRoles());
                }
            }
        }

    }

    public static void generateReport() {
        ArrayList<String> producingCompanies = new ArrayList<>();
        ArrayList<Credit> credits;
        ArrayList<Roles> roles;
        HashMap<Roles, Integer> frequencyMap;

        //finder navnene på alle producenterne
        for (Production production : cs.getProductions()) {
            producingCompanies.add(production.getCompany());
        }
        //finder alle rollerne for alle produktionerne for hver producent
        for (String companyName : producingCompanies) {

            credits = new ArrayList<>();
            roles = new ArrayList<>();
            frequencyMap = new HashMap<>();
            for (Production production : cs.getProductions()) {

                if (companyName.equalsIgnoreCase(production.getCompany())) {
                    credits.addAll(production.getCredits());
                    for (Credit credit : credits) {
                        roles.addAll(credit.getRoles());
                    }
                }
            }
            //tæller hvor mange instanser der er af hver rolle
            for (Roles r : roles) {
                Integer count = frequencyMap.get(r);
                if (count == null) {
                    count = 0;
                }
                frequencyMap.put(r, count + 1);
            }
            producers.put(companyName, frequencyMap);
        }
        System.out.println(producers);

        writeToFile(filename, producers);
    }

    public static void main(String[] args) {
        generateReport();
    }

}
