package controller.operations;

import controller.persistence.ServerDAO;
import controller.persistence.ServerDAODBImpl;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by ivan on 19/1/16.
 * Delete a persisted server (id of server to delete to be passed in as a command line arg)
 */
public class RemoveServer implements Operation{

    private ServerDAO daoDB;

    public RemoveServer(ServerDAODBImpl daoDB){
        this.daoDB = daoDB;
    }

    public void execute(){
        DataInputStream in =  new DataInputStream(System.in);
        try {
            System.out.println("Write the ID of the server to remove: ");
            int id = Integer.parseInt(in.readLine());
            boolean res = daoDB.removeServer(id);
            if(res) {
                System.out.println("Server removed");
            }else{
                System.out.println("There is not any server with ID: " + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NumberFormatException ex){
            System.out.println("You did not write a valid ID");
            System.out.println(ex.getMessage());
        }
    }

}
