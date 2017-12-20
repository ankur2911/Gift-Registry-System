package connection;
import java.sql.*;  

public class ConnectionClass {

	java.sql.Connection con = null;
	
	public Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/gift";
		String username = "root";
		String password = "1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		return con;
	}
	
}
