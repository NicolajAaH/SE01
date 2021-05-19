package usersystem.business;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import projectSDU2.business.domain.initialize.Server;
import projectSDU2.business.domain.initialize.SimpleObject;

import java.util.List;

import static org.junit.Assert.*;

public class ClientTest {

    static Client client;

    @BeforeClass
    public static void setUp() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(null);
            }
        }).start();
        Thread.sleep(100); //sleeper threaden, da serveren lige skal nå at starte op inden clienten forsøger at oprette forbindelse.
        client = Client.getInstance();
    }

    //søge efter produktioner/personer
    @Test
    public void testSearchedObjects() throws Exception{
        List<SimpleObject> result = client.searchedObjects("X Fac");
        int id = result.get(0).getId();
        String name = result.get(0).getName();
        assertEquals(1, id);
        assertEquals("X Factor", name);
        List<SimpleObject> result2 = client.searchedObjects("Mads");
        int id2 = result2.get(0).getId();
        String name2 = result2.get(0).getName();
        assertEquals(6, id2);
        assertEquals("Mads Mikkelsen", name2);
        List<SimpleObject> result3 = client.searchedObjects("XFac");
        assertEquals(true, result3.isEmpty());
    }

}