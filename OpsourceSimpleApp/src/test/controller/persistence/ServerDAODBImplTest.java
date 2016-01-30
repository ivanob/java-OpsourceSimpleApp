package controller.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import model.Server;
import java.util.List;

/**
 * Created by ivan on 20/1/16.
 */
public class ServerDAODBImplTest {
    private ServerDAODBImpl srvDB;

    @Before
    public void setUp() throws Exception {
        srvDB = new ServerDAODBImpl("res/mysql_conn.txt");
    }

    @After
    public void tearDown() throws Exception {
        srvDB.removeAllServers(); //Empty the table after the tests
        srvDB.closeConnection();
    }

    @Test
    public void testListAllServers() throws Exception {
        boolean ins1, ins2, ins3;
        Server s1 = new Server(20, "mockServer1");
        Server s2 = new Server(22, "mockServer2");
        Server s3 = new Server(23, "mockServer3");
        ins1 = srvDB.storeServer(s1);
        ins2 = srvDB.storeServer(s2);
        ins3 = srvDB.storeServer(s3);
        if(ins1 && ins2 && ins3){
            List<Server> listServs = srvDB.listAllServers();
            assertTrue("The expected number of servers listed is 3, and it actually is " + listServs, listServs.size()==3);
            assertTrue("The Server " + s1.name + " has not been listed correctly", listServs.get(0).id == s1.id);
            assertTrue("The Server " + s2.name + " has not been listed correctly", listServs.get(1).id == s2.id);
            assertTrue("The Server " + s3.name + " has not been listed correctly", listServs.get(2).id == s3.id);
        }else{
            fail("Impossible to prepare the DDBB to test listAllServers");
        }

    }

    @Test
    public void testRemoveServer() throws Exception {
        Server srvTest = new Server(20, "serverToDelete");
        boolean resInsert = srvDB.storeServer(srvTest);
        if(resInsert==true){
            boolean resDelete = srvDB.removeServer(srvTest.id);
            assertTrue("The method removeServer(...) has failed", resDelete);
        }else{
            fail("Impossible to prepare the DDBB to test delete");
        }
    }

    @Test
    public void testEditServer() throws Exception {
        Server srvTest = new Server(40, "serverToModify");
        boolean resInsert = srvDB.storeServer(srvTest);
        if(resInsert==true){
            String modifiedName = "serverModified";
            boolean resModify = srvDB.editServer(srvTest.id, modifiedName);
            assertTrue("The method editServer(...) has failed", resModify);
            List<Server> listServers = srvDB.listAllServers();
            assertTrue("The method editServer(...) has failed", listServers.get(0).name.compareTo(modifiedName)==0);
        }else{
            fail("Impossible to prepare the DDBB to test edit");
        }
    }

    @Test
    public void testStoreServer() throws Exception {
        Server srvTest = new Server(1, "server1");
        boolean res = srvDB.storeServer(srvTest);
        assertTrue("The method storeServer(...) has failed", res);
    }
}