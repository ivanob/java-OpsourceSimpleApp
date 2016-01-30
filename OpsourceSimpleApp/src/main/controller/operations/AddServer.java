package controller.operations;

import controller.persistence.ServerDAO;
import controller.persistence.ServerDAODBImpl;
import controller.persistence.ServerDAOXMLImpl;
import model.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ivan on 19/1/16.
 * Read a server "specification" from a file on local disk (e.g. see server_definition.xml)
 * and persist it to the SERVER database table
 */
public class AddServer implements Operation {

    private ServerDAODBImpl daoDB;
    private ServerDAOXMLImpl daoXML;

    public AddServer(ServerDAODBImpl daoDB, ServerDAOXMLImpl daoXML){
        this.daoDB = daoDB;
        this.daoXML = daoXML;
    }

    public void execute(){
        System.out.println("Write the name of the XML resource: ");
        try {
            DataInputStream in =  new DataInputStream(System.in);
            String res = in.readLine();
            daoXML.setNameResource(res);
            List<Server> listServ = daoXML.listAllServers();
            if(listServ.size()>0) {
                Server s = daoXML.listAllServers().get(0);
                daoDB.storeServer(s);
                System.out.println("Server added");
            }else{
                System.err.println("There was an error retrieving the description of the server (is the path right?)");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
