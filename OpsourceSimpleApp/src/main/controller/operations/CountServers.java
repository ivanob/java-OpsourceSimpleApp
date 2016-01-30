package controller.operations;

import controller.persistence.ServerDAO;
import controller.persistence.ServerDAODBImpl;

/**
 * Created by ivan on 19/1/16.
 * Count the number of servers currently persisted in the database
 */
public class CountServers implements Operation{

    private ServerDAO daoDB;

    public CountServers(ServerDAODBImpl daoDB){
        this.daoDB = daoDB;
    }

    public void execute(){
        int num = daoDB.listAllServers().size();
        System.out.println("There are " + num + " servers stored in the DDBB");
    }

}
