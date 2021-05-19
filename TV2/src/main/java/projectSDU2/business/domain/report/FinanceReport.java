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
    private static String filename = nameOfFile + index + ".txt";
    static HashMap<Roles, Integer> frequencyMap;

    public void generateFinanceReport() {
        top10credits = new HashMap<>();
        creditTypes = new HashMap<>();
        producers = new HashMap<>();
    }

    public static void generateFilmProducersReport() {
        ArrayList<String> producingCompanies = new ArrayList<>();
        ArrayList<Credit> credits;
        ArrayList<Roles> roles;

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
                    credits.addAll(production.getCredits());
                    for (Credit credit : credits) {
                        roles.addAll(credit.getRoles());
                    }
                }
                countFrequency(roles);
            }
            producers.put(companyName, frequencyMap);
        }
        writeToFile("Oversigt over producenter " + filename, producers);
    }

    //tæller hvor mange instanser der er af roller i en liste
    private static void countFrequency(ArrayList<Roles> list) {
        for (Roles r : list) {
            Integer count = frequencyMap.get(r);
            if (count == null) {
                count = 0;
            }
            frequencyMap.put(r, count + 1);
        }
    }

    public static void main(String[] args) {
        generateFilmProducersReport();
    }

}
