package connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Initial
 */
@WebServlet(name = "Initial", urlPatterns = "/initial", loadOnStartup = 0)
public class Initial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Initial() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException {
    	ConnectionClass obj = new  ConnectionClass();
        Connection con = null;
		try {
			con = obj.getConnection();
			create_tables(con);
			System.out.println("tables created in database");
			
		} catch(Exception e){
	    	e.printStackTrace();
		}
	    finally
	    {
	        try{
	           if(con!=null)
	              con.close();
	        }catch(SQLException se){
	           se.printStackTrace();
	        }
	     }
    }
	
	public void create_tables(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
	
	    String user = "CREATE TABLE IF NOT EXISTS USERS " +
        "(USERNAME VARCHAR(50), " +
        " PASSWORD VARCHAR(20), " + 
        " USER_ID int AUTO_INCREMENT, " + 
        " PRIMARY KEY (USER_ID))"; 

	    stmt.executeUpdate(user);
	    
	    String userInfo = "CREATE TABLE IF NOT EXISTS USER_INFO " +
	            "(USER_ID int, " +
	            " EMAIL VARCHAR(60) DEFAULT 'NONE', " + 
	            " QUESTION int DEFAULT 0, " + 
	            " ANSWER VARCHAR(70) DEFAULT 'NONE', " + 
	            " PRIMARY KEY (USER_ID),"+
	            "CONSTRAINT BK_PK FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))";

	    	    stmt.executeUpdate(userInfo);
	    
	    String userRegistry = "CREATE TABLE IF NOT EXISTS USER_REGISTRY " +
	            "(USER_ID int, " +
	            " REGISTRY_ID int,"+
	            " SHARE_STATUS INT," +
	            " CONSTRAINT BKA_PK PRIMARY KEY(REGISTRY_ID)," +
	            " CONSTRAINT U_FK FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))"; 

	    stmt.executeUpdate(userRegistry);
	       
	    
	    String shareRegistry = "CREATE TABLE IF NOT EXISTS VISIBLE " +
	            " (REGISTRY_ID int NOT NULL,"+
	            " USER_ID INT NOT NULL," + 
	            " CONSTRAINT BKA_FK2 FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))" ;

	    stmt.executeUpdate(shareRegistry);
	    
	    String itemTable = "CREATE TABLE IF NOT EXISTS ITEMS " +
	    		" (ITEM_CODE int,"+
	    		" NAME VARCHAR(30) NOT NULL," +
	            " COST DECIMAL(10,2)," +
	            " TYPE VARCHAR(30)," +
	            " DESCRIPTION VARCHAR(100)," +
	            " CONSTRAINT BKA_PK PRIMARY KEY(ITEM_CODE))";

	    stmt.executeUpdate(itemTable);
	    
	    String giftInventory = "CREATE TABLE IF NOT EXISTS Gift_Registry " +
	            "(USER_ID int, " +
	            " ITEM_CODE int,"+
	            " REGISTRY_ID int,"+
	            " ASSIGNED_USER VARCHAR(50) DEFAULT 'NONE',"+
	            " CONSTRAINT BKA_PK PRIMARY KEY(REGISTRY_ID, ITEM_CODE), " + 
	            " CONSTRAINT A_FK1 FOREIGN KEY(REGISTRY_ID) REFERENCES USER_REGISTRY(REGISTRY_ID), "+
	            " CONSTRAINT A_FK2 FOREIGN KEY(ITEM_CODE) REFERENCES ITEMS(ITEM_CODE), "+
	            " CONSTRAINT A_FK4 FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))"; 

	    stmt.executeUpdate(giftInventory);
	}

}
