package controller.operations;

import controller.persistence.ServerDAO;
import controller.persistence.ServerDAODBImpl;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by ivan on 19/1/16.
 * Edit name of persisted server (takes command line args to identify id of server and new name)
 */
public class EditNameServer implements Operation {

    private ServerDAO daoDB;

    public EditNameServer(ServerDAODBImpl daoDB){
        this.daoDB = daoDB;
    }

    public void execute(){
        DataInputStream in =  new DataInputStream(System.in);
        try {
            System.out.println("Write the ID of the server to modify: ");
            int id = Integer.parseInt(in.readLine());
            System.out.println("Write the new NAME of the server: ");
            String newName = in.readLine();
            daoDB.editServer(id, newName);
            System.out.println("Server edited");
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NumberFormatException ex){
            System.out.println("You did not write a valid ID");
            System.out.println(ex.getMessage());
        }
    }

}
