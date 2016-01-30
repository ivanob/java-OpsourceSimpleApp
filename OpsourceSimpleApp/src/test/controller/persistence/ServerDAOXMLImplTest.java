package controller.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import model.Server;
import java.util.List;
/**
 * Created by ivan on 21/1/16.
 */
public class ServerDAOXMLImplTest {

    private ServerDAOXMLImpl srvXML;

    @Before
    public void setUp() throws Exception {
        srvXML = new ServerDAOXMLImpl("res/server_1.xml");
    }

    @After
    public void tearDown() throws Exception {
        srvXML.closeConnection();
    }

    @Test
    public void testListAllServers() throws Exception {
        List<Server> listServers = srvXML.listAllServers();
        assertTrue("The method listAllServers of the XMLDAO has failed", listServers!=null && listServers.size()==1);
        assertTrue("The id of the Server is not the expected", listServers.get(0).id == 1);
        assertTrue("The name of the Server is not the expected", listServers.get(0).name.compareTo("MyServerName")==0);
    }
}