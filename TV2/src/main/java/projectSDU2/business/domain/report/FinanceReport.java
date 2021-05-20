package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FinanceReport extends Report {
    //Attributter
    private LinkedHashMap<String, Integer> top10credits;
    private HashMap<Roles, Integer> creditTypes;
    private HashMap<String, HashMap<Roles, Integer>> producers;
    private static int index = 1;
    private String filename = "Finansrapport " + nameOfFile + index + ".txt";
    private HashMap<Roles, Integer> frequencyMap;
    private ArrayList<Credit> credits;
    private ArrayList<Roles> roles;

    //Udskriver en samlet rapport
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
            index++; //Inkrementerer index
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Genererer en liste over alle producenterne i databasen, samt et overblik over hvilke roller der er brugt og i hvilket omfang
    private void generateFilmProducers() {
        producers = new HashMap<>();
        ArrayList<String> producingCompanies = new ArrayList<>();

        //Finder navnene på alle producenterne
        for (Production production : cs.getProductions()) {
            if (!producingCompanies.contains(production.getCompany()))
                producingCompanies.add(production.getCompany());
        }
        //Finder alle rollerne for alle produktionerne for hver producent
        for (String companyName : producingCompanies) {
            frequencyMap = new HashMap<>();
            for (Production production : cs.getProductions()) {
                credits = new ArrayList<>();
                roles = new ArrayList<>();
                if (companyName.equalsIgnoreCase(production.getCompany())) { //Hvis firmanavn er ens, case insensitive
                    getCreditsAndRolesFromProduction(production);
                }
                countFrequencyRoles(roles, frequencyMap);
            }
            producers.put(companyName, frequencyMap);
        }
    }

    //Genererer en samlet liste med alle roller og hvor hyppigt de forekommer i databasen
    private void generateRolesOverview() {
        creditTypes = new HashMap<>();
        roles = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            credits = new ArrayList<>();
            getCreditsAndRolesFromProduction(production);
        }
        countFrequencyRoles(roles, creditTypes);
    }

    //Generere en liste over de top 10 mest krediterede personer
    private void generateTop10MostCredited() {
        HashMap<String, Integer> map = new HashMap<>();
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        credits = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            credits.addAll(production.getCredits());
        }
        for (Credit credit : credits) {
            if (map.containsKey(credit.getPerson().getName())) { //Hvis den indeholder navnet
                map.put(credit.getPerson().getName(),
                        map.get(credit.getPerson().getName()) + credit.getRoles().size());
            } else { //Indeholder ikke navnet på personen
                map.put(credit.getPerson().getName(), credit.getRoles().size());
            }
        }
        //Sorterer listen over medvirkende og finder top 10 medvirkende med fleste krediteringer
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10)
                .forEach(value -> sortedMap.put(value.getKey(), value.getValue())); //Tager mappet og sorterer det ud fra en comparator der reverser (fra høj til lav)
        //Derefter limites den til 10, da kun top 10 ønskes, og derefter itereres der igennem de 10 med for each loop med hvert set (Key, value)
        //Hver af de værdier tilføjes til et LinkedHashMap (sortedMap) som bevarer insertion order, og dermed opnås top 10
        top10credits = sortedMap; //Sætter top10credits til sortedMap
    }

    //Tæller hvor mange instanser der er af roller i en liste
    private void countFrequencyRoles(ArrayList<Roles> list, HashMap<Roles, Integer> map) {
        for (Roles r : list) {
            Integer count = map.get(r); //For hver role i listen, få count
            if (count == null) { //Er den ikke talt endnu sættes den til 0 for at oprette den
                count = 0;
            }
            map.put(r, count + 1);
        }
    }

    //Udtrækker alle krediteringer og roller for en given production
    private void getCreditsAndRolesFromProduction(Production production) {
        credits.addAll(production.getCredits());
        for (Credit credit : credits) {
            roles.addAll(credit.getRoles());
        }
    }
}
