package projectSDU2.business.domain;

import javafx.collections.ObservableList;
import projectSDU2.Interfaces.DomainI;
import projectSDU2.Interfaces.PersistenceI;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

@SuppressWarnings("DuplicatedCode")
public class DomainConnect implements DomainI {

    //Attributter
    private CreditingSystem creditingSystem = CreditingSystem.getInstance();

    //Autoriserer en bruger
    @Override
    public boolean authorize(String email, String password) {
        return creditingSystem.authorizeAccount(email, password);
    }

    //Kører setup i creditingSystem, som opdaterer alle listerne
    @Override
    public void runSetup() {
        creditingSystem.setup();
    }

    //Tilføjer en producer til systemet
    @Override
    public void addProducer(String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().put(new Person(name, phone, email, password, "producer"), "personmapper");
    }

    //Henter alle produktioner
    @Override
    public ArrayList<Production> getProductions() {
        return creditingSystem.getProductions();
    }

    //Finder en produktion ud fra et id
    @Override
    public Production findProduction(int id) {
        return creditingSystem.findProduction(id);
    }

    //Henter alle roller
    @Override
    public ArrayList<Roles> getRoles() {
        return creditingSystem.getRolesdb();
    }

    //Opretter en credit ud fra id og roller
    @Override
    public Credit createCredit(int id, ObservableList observableList) {
        ArrayList<Roles> roles = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            roles.add((Roles) observableList.get(i)); //konverter listen til roller
        }
        Person person = findPerson(id); //find person
        return new Credit(0, person, roles); //sæt id til 0, da den gives automatisk af databasen
    }

    //Find role ud fra rolenavn
    @Override
    public Roles findRole(String role) {
        return Roles.valueOf(role);
    }

    //Tilføj en produktion ud fra liste med credits, navn og firma
    @Override
    public void addProduction(ObservableList observableList, String name, String company) {
        ArrayList<Credit> credits = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            credits.add((Credit) observableList.get(i)); //konverter listen til credits
        }
        CreditingSystem.getPersistenceI().getFacade().put(new Production(credits, company, name), "productionmapper"); //opretter den
    }

    //Finder en person ud fra et id
    @Override
    public Person findPerson(int id) {
        return creditingSystem.findPerson(id);
    }

    //Finder en person ud fra email
    @Override
    public Person findPerson(String email) {
        return creditingSystem.findPerson(email);
    }

    //Henter alle producers
    @Override
    public ArrayList<Person> getProducers() {
        ArrayList<Person> producers = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()) {
            if (person.getType().equals("producer")) {
                producers.add(person);
            }
        }
        return producers;
    }

    //Returnerer en person tilbage ud fra et objekt, for at holde lagdelingen
    @Override
    public Person castToPerson(Object object) {
        return (Person) object;
    }

    //Henter alle participants
    @Override
    public ArrayList<Person> getParticipants() {
        ArrayList<Person> participants = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()) {
            if (person.getType().equals("participant")) {
                participants.add(person);
            }
        }
        return participants;
    }

    //Caster til en production for at overholde lagdelingen
    @Override
    public Production castToProduction(Object object) {
        return (Production) object;
    }

    //Tilføjer en participant til databasen
    @Override
    public void addParticipant(String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().put(new Person(name, phone, email, password, "participant"), "personmapper");
        creditingSystem.setup();
    }

    //Ændrer en person i databasen
    @Override
    public void editPerson(int oid, String name, int phone, String email, String password) {
        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Person(name, phone, email, password, "person"), "personmapper");
        creditingSystem.setup();
    }

    //Caster til en credit for at overholde lagdelingen
    @Override
    public Credit castToCredit(Object object) {
        return (Credit) object;
    }

    //Ændrer en credit ud fra et produktionsid, personid og roller
    @Override
    public void editCredit(int productionID, int personID, ObservableList observableList) {
        ArrayList<Roles> roles = new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) { //laver listen om til roller
            roles.add((Roles) observableList.get(i));
        }
        Person person = findPerson(personID); //Finder personen
        CreditingSystem.getPersistenceI().getFacade().edit(productionID, new Credit(person, roles), "creditmapper"); //ændrer det i databasen
    }

    //Ændrer en produktion ud fra diverse attributter, der indgår i en produktion
    @Override
    public void editProduction(int oid, String name, String company, ObservableList credits, boolean status, boolean sent) {
        ArrayList<Credit> creditsList = new ArrayList<>();
        for (int i = 0; i < credits.size(); i++) { //laver listen om til credits
            creditsList.add((Credit) credits.get(i));
        }
        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Production(creditsList, company, name, status, sent), "productionmapper"); //ændrer i db
    }

    //Ændrer person ud fra diverse attributter, som en person har
    @Override
    public void editPerson(int oid, String name, int phone, String email, String password, String type) {
        CreditingSystem.getPersistenceI().getFacade().edit(oid, new Person(name, phone, email, password, type), "personmapper"); //ændrer i db
        creditingSystem.setup(); //opdaterer systemets lister så den har det nyeste fra databasen

    }

    //Sletter en person ud fra et id
    @Override
    public void deletePerson(int id) {
        CreditingSystem.getPersistenceI().getFacade().delete(id, "personmapper"); //sletter personen i db
        creditingSystem.setup(); //opdaterer systemets lister
    }

    //Sletter en produktion ud fra et id
    @Override
    public void deleteProduction(int productionID) {
        CreditingSystem.getPersistenceI().getFacade().delete(productionID, "productionmapper"); //sletter personen i db
    }

    //Sletter en credit ud fra et id
    @Override
    public void deleteCredit(int creditID) {
        CreditingSystem.getPersistenceI().getFacade().delete(creditID, "creditmapper"); //sletter credit i db
    }

    //Genererer krediteringsrapport
    @Override
    public void generateCreditingReport() {
        creditingSystem.generateCreditingReport();
    }

    //Søger efter produktioner ud fra en streng
    @Override
    public ArrayList<Production> searchProductions(String search) {
        ArrayList<Production> matchingProductions = new ArrayList<>();
        for (Production production : creditingSystem.getProductions()) {
            String id = production.getProductionID() + "";
            String searchID = id.toLowerCase();
            String name = production.getName();
            String searchName = name.toLowerCase();
            String searchInput = search.toLowerCase(); //Gør alt til lowercase for at det er case insensitive
            if (searchID.contains(searchInput) || searchName.contains(searchInput)) { //hvis id eller navn på produktionen indeholder søgestrengen tilføjes den til listen
                matchingProductions.add(production);
            }
        }
        return matchingProductions;
    }

    //Finder produktioner hvor en producer er producer ud fra et id
    @Override
    public ArrayList<Production> findWhereProducer(int producerID) {
        return creditingSystem.findWhereProducer(producerID);
    }

    //Søger efter producers ud fra en streng
    @Override
    public ArrayList<Person> searchProducers(String search) {
        String searchInput = search.toLowerCase(); //case insensitive
        ArrayList<Person> matchingPersons = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()) {
            if (person.getType().equals("producer")) {
                String id = person.getId() + "";
                String searchID = id.toLowerCase();
                String name = person.getName();
                String searchName = name.toLowerCase();
                if (searchID.contains(searchInput) || searchName.contains(searchInput)) { //hvis id eller navn indeholder søgestrengen tilføjes den
                    matchingPersons.add(person);
                }
            }
        }
        return matchingPersons;
    }

    //Søger efter participants ud fra en streng
    @Override
    public ArrayList<Person> searchParticipants(String search) {
        String searchInput = search.toLowerCase(); //case insensitive
        ArrayList<Person> matchingPersons = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()) {
            if (person.getType().equals("participant")) {
                String id = person.getId() + "";
                String searchID = id.toLowerCase();
                String name = person.getName();
                String searchName = name.toLowerCase();
                if (searchID.contains(searchInput) || searchName.contains(searchInput)) { //hvis id eller navn indeholder søgestrengen tilføjes den
                    matchingPersons.add(person);
                }
            }
        }
        return matchingPersons;
    }

    //Returnerer alle produktioner der ikke er valideret endnu
    @Override
    public ArrayList<Production> getNotValidated() {
        ArrayList<Production> notValidated = new ArrayList<>();
        for (Production production : creditingSystem.getProductions()) {
            if (!production.isStatus()) {
                notValidated.add(production);
            }
        }
        return notValidated;
    }

    //Returnerer PersistenceI
    @Override
    public PersistenceI getPersistenceI() {
        return CreditingSystem.getPersistenceI();
    }

    //Returnerer alle personer undtagen systemadministratorer
    @Override
    public ArrayList<Person> getPersons() {
        ArrayList<Person> matchingPersons = new ArrayList<>();
        for (Person person : creditingSystem.getPersons()) {
            if (!person.getType().equals("systemadministrator")) {
                matchingPersons.add(person); //credit kan ikke gives til systemadmin
            }
        }
        return matchingPersons;
    }

    //Søger i alle persons undtagen systemadministratorer
    @Override
    public ArrayList<Person> searchPersons(String search) {
        String searchInput = search.toLowerCase(); //case insensitive
        ArrayList<Person> matchingPersons = getPersons(); //alle personer undtagen systemadministratorer
        ArrayList<Person> matchingSearchPersons = new ArrayList<>();
        for (Person person : matchingPersons) {
            String id = person.getId() + "";
            String searchID = id.toLowerCase();
            String name = person.getName();
            String searchName = name.toLowerCase();
            if (searchID.contains(searchInput) || searchName.contains(searchInput)) { //hvis id eller navn indeholder strengen tilføjes den
                matchingSearchPersons.add(person);
            }
        }
        return matchingSearchPersons;
    }

    //Merger to personer sammen ud fra id'er fra dem + nye attributter
    @Override
    public void merge(int person1ID, int person2ID, String name, int phone, String email, String password, String type) {
        Person person = new Person(name, phone, email, password, type); //person ud fra de nye attributter (den nye person)
        //Opretter en midlertidig person som svarer til personen med første ID, og sætter mail til temp1, da mails skal være unikke i databasen
        Person person1Delete = new Person(creditingSystem.findPerson(person1ID).getName(), creditingSystem.findPerson(person1ID).getPhone(),
                "temp1", creditingSystem.findPerson(person1ID).getPassword(), creditingSystem.findPerson(person1ID).getType());
        //Opretter en midlertidig person som svarer til personen med andet ID, og sætter mail til temp1, da mails skal være unikke i databasen
        Person person2Delete = new Person(creditingSystem.findPerson(person2ID).getName(), creditingSystem.findPerson(person2ID).getPhone(),
                "temp2", creditingSystem.findPerson(person2ID).getPassword(), creditingSystem.findPerson(person2ID).getType());
        CreditingSystem.getPersistenceI().getFacade().edit(person1ID, person1Delete, "personmapper"); //sætter mailen til temp1
        CreditingSystem.getPersistenceI().getFacade().edit(person2ID, person2Delete, "personmapper"); //sætter mailen til temp2
        CreditingSystem.getPersistenceI().getFacade().put(person, "personmapper"); //putter den nye person i databasen
        Person finalPerson = (Person) CreditingSystem.getPersistenceI().getFacade().getAll("personmapper")
                .get(CreditingSystem.getPersistenceI().getFacade().getAll("personmapper").size() - 1); //Henter den nye oprettede person

        for (Production production : creditingSystem.getProductions()) {
            for (Credit credit : production.getCredits()) {
                if (credit.getPerson().getId() == person1ID || credit.getPerson().getId() == person2ID) {
                    credit.setPerson(finalPerson); //sætter alle credits der tilhører de to gamle personer til den nye mergede person
                    CreditingSystem.getPersistenceI().getFacade().edit(production.getProductionID(), credit, "creditmapper"); //tilføjer creditsne i db
                }
            }
        }

        CreditingSystem.getPersistenceI().getFacade().delete(person1ID, "personmapper"); //sletter den første gamle person
        CreditingSystem.getPersistenceI().getFacade().delete(person2ID, "personmapper"); //sletter den anden gamle person
    }

    //Genererer finansrapport
    @Override
    public void generateFinanceReport() {
        creditingSystem.generateFinanceReport();
    }

}
