package controller.operations;

import controller.persistence.ServerDAO;
import controller.persistence.ServerDAODBImpl;
import model.Server;

import java.util.List;

/**
 * Created by ivan on 19/1/16.
 * List persisted servers
 */
public class ListServers implements Operation{

    private ServerDAO daoDB;

    public ListServers(ServerDAODBImpl daoDB){
        this.daoDB = daoDB;
    }

    public void execute(){
        List<Server> listServers = daoDB.listAllServers();
        System.out.println("List of all servers stored: ");
        for(Server s : listServers){
            System.out.println(s);
        }
    }

}
