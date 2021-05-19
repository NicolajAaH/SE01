package projectSDU2.business.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.*;
import projectSDU2.Interfaces.DomainI;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;
import projectSDU2.technicalServices.PersistenceHandler;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class DomainConnectTest {
    private static DomainI domainI;

    @BeforeClass
    public static void setUp() throws Exception {
        PersistenceHandler.setDatabaseName("tv2test");
        domainI = new DomainConnect();

    }

    @Test //tilføj produktion med tilhørende kreditering
    public void addGetRemoveProduction(){
        ArrayList<Credit> credits = new ArrayList<>();
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(Roles.Actor);
        roles.add(Roles.Producer);
        credits.add(new Credit(domainI.findPerson(2), roles));
        ObservableList observableList = FXCollections.observableArrayList(credits);
        domainI.addProduction(observableList, "Titanic", "Paramount Pictures"); //tilføjer produktionen
        Production titanic = domainI.searchProductions("Titanic").get(0); //henter den nye oprettede
        assertEquals("Titanic", titanic.getName());
        assertEquals("Paramount Pictures", titanic.getCompany());
        assertEquals("Thomas Blachman", titanic.getCredits().get(0).getPerson().getName());
        assertEquals(true, titanic.getCredits().get(0).getRoles().contains(Roles.Actor));
        assertEquals(true, titanic.getCredits().get(0).getRoles().contains(Roles.Producer));
        domainI.deleteProduction(titanic.getProductionID()); //sletter den igen
        assertEquals(0, domainI.searchProductions("Titanic").size());
    }

    @Test //oprette personer
    public void addGetRemovePerson(){
        domainI.addParticipant("Leonardo DiCaprio", 22222222, "leo@test.com", "diCap"); //tilføj person
        Person leo = domainI.findPerson("leo@test.com"); //hent personen
        assertEquals("Leonardo DiCaprio", leo.getName());
        assertEquals(22222222, leo.getPhone());
        assertEquals("diCap", leo.getPassword());
        assertEquals("leo@test.com", leo.getEmail());
        domainI.deletePerson(leo.getId()); //slet personen
        assertEquals(null, domainI.findPerson("leo@test.com"));
    }

    @Test //administrere krediteringer + ændre egne produktioner
    public void editProduction(){
        ArrayList<Credit> credits = new ArrayList<>();
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(Roles.Actor);
        roles.add(Roles.Producer);
        credits.add(new Credit(domainI.findPerson(2), roles));
        ObservableList observableList = FXCollections.observableArrayList(credits);
        domainI.addProduction(observableList, "Titanic", "Paramount Pictures"); //tilføjer produktionen
        Production titanic = domainI.searchProductions("Titanic").get(0); //henter den nye oprettede
        domainI.editProduction(titanic.getProductionID(), "Titanic", "AndetFirma", observableList, false, false);
        Production titanicNew = domainI.searchProductions("Titanic").get(0); //henter den opdaterede
        assertEquals("Titanic", titanicNew.getName());
        assertEquals("AndetFirma", titanicNew.getCompany());
        assertEquals(false, titanicNew.isStatus());
        assertEquals(false, titanicNew.isSent());
        domainI.deleteProduction(titanicNew.getProductionID()); //sletter den igen
    }

    @Test //administrere brugere
    public void editPerson(){
        domainI.addParticipant("Leonardo DiCaprio", 22222222, "leo@test.com", "diCap"); //tilføj person
        Person leo = domainI.findPerson("leo@test.com"); //hent personen
        domainI.editPerson(leo.getId(), "Leonardo DiCaprio", 22222222, "leo@test.com", "newPass");
        Person newLeo = domainI.findPerson("leo@test.com"); //hent opdateret personen
        assertEquals("newPass", newLeo.getPassword());
        assertEquals("Leonardo DiCaprio", newLeo.getName());
        assertEquals(22222222, newLeo.getPhone());
        domainI.deletePerson(newLeo.getId());
    }

    @Test //merge personer
    public void mergePerson(){
        domainI.addParticipant("Leonardo DiCaprio", 22222222, "leo@test.com", "diCap"); //tilføj person
        domainI.addParticipant("Leonardo DiCaprio", 22222222, "leo@titanic.com", "diCap"); //tilføj person
        Person leo = domainI.findPerson("leo@test.com"); //hent personen
        Person leo2 = domainI.findPerson("leo@titanic.com"); //hent personen
        domainI.merge(leo.getId(), leo2.getId(), leo.getName(), leo.getPhone(), leo2.getEmail(), leo.getPassword(), leo.getType());
        Person leoMerge = domainI.findPerson("leo@titanic.com"); //hent personen
        assertEquals("leo@titanic.com", leoMerge.getEmail());
        assertEquals("Leonardo DiCaprio", leoMerge.getName());
        assertEquals(22222222, leoMerge.getPhone());
        assertEquals("diCap", leoMerge.getPassword());
        domainI.deletePerson(leoMerge.getId());
    }

    @Test //genere krediteringsrapport + validering
    public void generateCreditingReport(){
        ArrayList<Credit> credits = new ArrayList<>();
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(Roles.Actor);
        roles.add(Roles.Producer);
        credits.add(new Credit(domainI.findPerson(2), roles));
        ObservableList observableList = FXCollections.observableArrayList(credits);
        domainI.addProduction(observableList, "Titanic", "Paramount Pictures"); //tilføjer produktionen
        Production titanic = domainI.searchProductions("Titanic").get(0); //henter den nye oprettede
        domainI.editProduction(titanic.getProductionID(), "Titanic", "AndetFirma", observableList, true, false); //validerer den (status)
        Production titanicNew = domainI.searchProductions("Titanic").get(0); //henter den nye ændrede
        domainI.generateCreditingReport();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        String todayNoTime = format.format(today);
        int index = 0;
        String nameOfFile = "Krediteringsrapport " + todayNoTime + " ID " + index + ".txt";
        File file = new File(nameOfFile); //Laver et fil objekt
        assertEquals(true, file.exists());
        domainI.deleteProduction(titanicNew.getProductionID()); //sletter den igen
        assertEquals(true, file.delete()); //sletter filen igen
    }

    @AfterClass
    public static void tearDown() throws Exception {
        PersistenceHandler.setDatabaseName("tv2");
    }
}