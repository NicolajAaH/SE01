package projectSDU2.business.domain.initialize;

import org.junit.Test;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.user.Person;

import static org.junit.Assert.*;

public class CreditingSystemTest {
    static CreditingSystem creditingSystem;

    @org.junit.BeforeClass
    public static void setUp() throws Exception {
        creditingSystem = CreditingSystem.getInstance();
    }

    @Test
    public void testFindPerson(){
        int id = creditingSystem.findPerson("mort@admin-tv2.dk").getId();
        assertEquals(1, id);
    }

    @Test
    public void testFindProduction(){
        int id = creditingSystem.findProduction(1).getProductionID();
        assertEquals(1, id);
    }

    //logge ind/ud
    @Test
    public void testAuthorizeAccount(){
        boolean result = creditingSystem.authorizeAccount("mort@admin-tv2.dk", "adminpass");
        assertEquals(true, result);
        boolean result2 = creditingSystem.authorizeAccount("mort@admin-tv2.dk", "adminpass12");
        assertNotEquals(true, result2);
    }


}