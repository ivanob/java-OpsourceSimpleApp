import controller.operations.OperationFactory;
import controller.persistence.ServerDAODBImpl;
import controller.persistence.ServerDAOXMLImpl;
import model.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by ivan on 23/1/16.
 */
public class IntegrationTest {

    private ServerDAODBImpl dbDao;
    private ServerDAOXMLImpl xmlDao;

    @Before
    public void setUp() throws Exception {
        dbDao = new ServerDAODBImpl("res/mysql_conn.txt");
        xmlDao = new ServerDAOXMLImpl("res/server_1.xml");
        dbDao.removeAllServers(); //Empty the database
    }

    @After
    public void tearDown() throws Exception {
        dbDao.removeAllServers(); //Empty the database
        dbDao.closeConnection();
        xmlDao.closeConnection();
    }

    @Test
    public void testIntegration() throws Exception {
        int numServers = dbDao.listAllServers().size();
        assertTrue("The ddbb should be empty, and it contains " + numServers + " servers", numServers == 0);
        //Insert 1 server
        Server s1 = new Server(1, "testServer1");
        dbDao.storeServer(s1);
        numServers = dbDao.listAllServers().size();
        assertTrue("The ddbb should have 1 server, and it contains " + numServers, numServers == 1);
        Server sExpected1 = dbDao.listAllServers().get(0);
        assertTrue("The Server retrieved is not the expected", sExpected1.equals(s1));
        //Try to remove a server which doesnt exist
        dbDao.removeServer(444);
        dbDao.removeServer(-1);
        numServers = dbDao.listAllServers().size();
        assertTrue("The ddbb should have 1 server, and it contains " + numServers, numServers == 1);
        //Remove the server added
        dbDao.removeServer(1);
        numServers = dbDao.listAllServers().size();
        assertTrue("The ddbb should be empty, and it contains " + numServers + " servers", numServers == 0);
        //Add the server again
        dbDao.storeServer(s1);
        //Edit the name
        String newName = "newNameForOldServer";
        dbDao.editServer(1, newName);
        assertTrue("The server doesnt have the new name", dbDao.listAllServers().get(0).name.compareTo(newName)==0);
        //Try to edit the name of a server which does not exist
        dbDao.editServer(222, "newName");
        //Check again that the name of the server has not been modified
        assertTrue("The server doesnt have the new name", dbDao.listAllServers().get(0).name.compareTo(newName)==0);
        //Read the especification from a XML file
        List<Server> listServers = xmlDao.listAllServers();
        assertTrue("The xml doesnt contain a valid description", listServers.size()==1);
        Server sXml = listServers.get(0);
        assertTrue("The server read from XML is not the expected", sXml.id == 1 && sXml.name.compareTo("MyServerName")==0);
    }
}
