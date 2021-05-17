package projectSDU2.business.domain.report;

import org.junit.Test;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreditingReportTest {
    private ArrayList<Credit> credits = new ArrayList<>();
    private ArrayList<Roles> roles = new ArrayList<>();
    private Production production;

    @org.junit.Before
    public void setUp() throws Exception {
        roles.add(Roles.Light);
        roles.add(Roles.Producer);
        credits.add(new Credit(new Person("Test", 12341234, "test@test", "testpass", "producer"), roles));
        production = new Production(credits, "TestCompany", "TestName");
    }

    @Test
    public void testReport(){

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
}