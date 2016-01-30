package view;

import controller.operations.OperationFactory;

import java.lang.*;
import java.sql.*;
import java.io.*;
import controller.operations.Operation;

public class MainApp
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
    {
	boolean running = true;

	showHelp();

	while(running)
	{
		DataInputStream in =  new DataInputStream(System.in);
            String option = in.readLine();

	    if(option.equals("help")){
			showHelp();
		}
	    else if(option.equals("quit")){
			running = false;
	    }
	    else{
			Operation op = OperationFactory.instance.getOperation(option);
			if(op != null) {
				op.execute();
			}else{
				System.out.println("Incorrect operation");
			}
	    }
	}
	OperationFactory.instance.freeResources(); //Close connections to DDBB and Files
    }

    private static void showHelp()
    {
	System.out.println("help to display this message");
	System.out.println("countServers to display the current number of servers present");
	System.out.println("addServer to display the current number of servers present");
	System.out.println("editServer to change the name of a server identified by id (takes 2 additional args - the id and the new name)");
	System.out.println("deleteServer to delete a server (takes one more arg - the id of the server to delete)");
	System.out.println("listServers to display details of all servers servers");
    }
}
