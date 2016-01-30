package controller.persistence;

import model.Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

/**
 * Created by ivan on 19/1/16.
 */
public class ServerDAODBImpl implements ServerDAO{

    private String JDBC_DRIVER;
    private String DB_URL;
    private String user, pass;
    private Connection conn;

    private void readConfigFile(String fileName){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            JDBC_DRIVER = br.readLine();
            DB_URL = br.readLine();
            user = br.readLine();
            pass = br.readLine();
            br.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * It opens the connection to the ddbb and gets ready to receive orders
     * @param nameResource the path of the file with the parameters to access the ddbb
     */
    public ServerDAODBImpl(String nameResource){
        readConfigFile(nameResource);
        conn = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,user,pass);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public List<Server> listAllServers() {
        Statement stmt = null;
        List<Server> listServers = new LinkedList<>();
        try{
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM SERVER";
            ResultSet rs = stmt.executeQuery(sql);
            //Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String id  = rs.getString("ID");
                String name = rs.getString("NAME");
                Server s = new Server(Integer.parseInt(id), name);
                listServers.add(s);
            }
            //Clean-up environment
            rs.close();
            stmt.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
        }
        return listServers;
    }


    public boolean removeServer(int id) {
        try {
            String removeQuery = "delete from SERVER where id=?";
            PreparedStatement preparedStmt = conn.prepareStatement(removeQuery);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();
        }
        catch(SQLException e){
            System.err.println("Error removing an existing Server in the DDBB");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean editServer(int id, String newName) {
        try {
            String updateQuery = "UPDATE server " +
                    "SET name = ? WHERE id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(updateQuery);
            preparedStmt.setString(1, newName);
            preparedStmt.setInt(2, id);
            int res = preparedStmt.executeUpdate();
            if(res==-1){
                return false;
            }
        }
        catch(SQLException e){
            System.err.println("Error modifying a Server in the DDBB");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean storeServer(Server srv) {
        try {
            String insertQuery = "insert into SERVER (id, name)"
                    + " values (?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(insertQuery);
            preparedStmt.setString(1, Integer.toString(srv.id));
            preparedStmt.setString(2, srv.name);
            preparedStmt.execute();
        }
        catch(SQLException e){
            System.err.println("Error inserting new Server in the DDBB");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean removeAllServers(){
        try{
            Statement st = conn.createStatement();
            String sql = "DELETE FROM SERVER";
            st.executeUpdate(sql);
        }
        catch(SQLException s){
            System.err.println("SQL statement is not executed!");
            return false;
        }
        return true;
    }
}
