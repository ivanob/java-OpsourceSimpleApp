package controller.persistence;

import model.Server;
import java.util.List;

/**
 * Created by ivan on 19/1/16.
 * It implements the DAO pattern. There are two subclasses here: ServerDAODBImpl and ServerDAOXMLImpl.
 * The first one access the DDBB and persists the data using SQL and the second one works with XML files.
 */
public interface ServerDAO {

    public List<Server> listAllServers();
    public boolean removeServer(int id);
    public boolean editServer(int id, String newName);
    public boolean storeServer(Server srv);
    public void closeConnection();
}
