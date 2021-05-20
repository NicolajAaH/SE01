package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FinanceReport extends Report {
    private HashMap<String, Integer> top10credits;
    private HashMap<Roles, Integer> creditTypes;
    private HashMap<String, HashMap<Roles, Integer>> producers;
    private static int index = 1;
    private String filename = "Finansrapport " + nameOfFile + index + ".txt";
    private HashMap<Roles, Integer> frequencyMap;
    private ArrayList<Credit> credits;
    private ArrayList<Roles> roles;

    //udskriver en samlet rapport
    public void generateFinanceReport() {
        generateFilmProducers();
        generateRolesOverview();
        generateTop10MostCredited();
        try (FileWriter file = new FileWriter(filename)) {
            file.write("Oversigt over producenter \n");
            file.append(gson.toJson(producers));
            file.append("\nOversigt over roller \n");
            file.append(gson.toJson(creditTypes));
            file.append("\nTop 10 mest krediterede \n");
            file.append(gson.toJson(top10credits));
            index++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //generere en liste over alle producenterne i databasen, samt et overblik over hvilke roller der er brugt og i hvilket omfang
    private void generateFilmProducers() {
        producers = new HashMap<>();
        ArrayList<String> producingCompanies = new ArrayList<>();

        //finder navnene på alle producenterne
        for (Production production : cs.getProductions()) {
            if (!producingCompanies.contains(production.getCompany()))
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
    }

    //generere en samlet liste med alle roller og hvor hyppigt de forekommer i databasen
    private void generateRolesOverview() {
        creditTypes = new HashMap<>();
        roles = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            credits = new ArrayList<>();
            getCreditsAndRolesFromProduction(production);
        }
        countFrequencyRoles(roles, creditTypes);
    }

    //generere en liste over de top 10 mest krediterede personer
    private void generateTop10MostCredited() {
        HashMap<String, Integer> map = new HashMap<>();
        credits = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            credits.addAll(production.getCredits());
        }
        for (Credit credit : credits) {
            if (map.containsKey(credit.getPerson().getName())) {
                map.put(credit.getPerson().getName(),
                        map.get(credit.getPerson().getName()) + credit.getRoles().size());
            } else {
                map.put(credit.getPerson().getName(), credit.getRoles().size());
            }
        }
        //Sorterer listen over medvirkende og finder top 10 medvirkende med fleste krediteringer
        top10credits = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    //tæller hvor mange instanser der er af roller i en liste
    private void countFrequencyRoles(ArrayList<Roles> list, HashMap<Roles, Integer> map) {
        for (Roles r : list) {
            Integer count = map.get(r);
            if (count == null) {
                count = 0;
            }
            map.put(r, count + 1);
        }
    }

    //udtrækker alle krediteringer og roller for en given production
    private void getCreditsAndRolesFromProduction(Production production) {
        credits.addAll(production.getCredits());
        for (Credit credit : credits) {
            roles.addAll(credit.getRoles());
        }
    }
}
