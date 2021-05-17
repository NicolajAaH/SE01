package projectSDU2.business.domain.initialize;

import org.junit.Test;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import static org.junit.Assert.*;

public class CreditingSystemTest {
    CreditingSystem creditingSystem = CreditingSystem.getInstance();

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFindPerson(){
        int id = creditingSystem.findPerson("producer@test.dk").getId();
        assertEquals(1, id);
    }

    @Test
    public void testFindProduction(){
        int id = creditingSystem.findProduction(25).getProductionID();
        assertEquals(25, id);
    }

    @Test
    public void testAuthorizeAccount(){
        boolean result = creditingSystem.authorizeAccount("producer@test.dk", "producer123");
        assertEquals(true, result);
        boolean result2 = creditingSystem.authorizeAccount("producer@test.dk", "producer12");
        assertNotEquals(true, result2);
    }



    @org.junit.After
    public void tearDown() throws Exception {
    }

}