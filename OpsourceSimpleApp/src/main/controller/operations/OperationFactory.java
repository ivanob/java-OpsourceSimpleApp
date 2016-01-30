package controller.operations;

import controller.persistence.*;
/**
 * Created by ivan on 21/1/16.
 * Factory pattern to retrieve the Operations supported by the application. It is also
 * a Singleton, so it can be easily accessed from any point of the application
 * without the need to duplicate the objects (note that the DAO classes could need
 * expensive resources to work).
 */
public class OperationFactory {
    private ServerDAODBImpl daoDB;
    private ServerDAOXMLImpl daoXML;

    public static OperationFactory instance = new OperationFactory();

    private OperationFactory(){
        daoDB = new ServerDAODBImpl("res/mysql_conn.txt");
        daoXML = new ServerDAOXMLImpl("res/server_1.xml");
    }

    public Operation getOperation(String name){
        Operation op;
        switch(name){ //Valid in Java7
            case "countServers":
                op = new CountServers(daoDB);
                break;
            case "addServer":
                op = new AddServer(daoDB, daoXML);
                break;
            case "deleteServer":
                op = new RemoveServer(daoDB);
                break;
            case "editServer":
                op = new EditNameServer(daoDB);
                break;
            case "listServers":
                op = new ListServers(daoDB);
                break;
            default:
                op=null;
        }
        return op;
    }

    public void freeResources(){
        daoDB.closeConnection();
        daoXML.closeConnection();
    }
}
