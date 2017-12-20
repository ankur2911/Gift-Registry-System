package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ValidUser {

	public int checkEncryption(String given) {
		System.out.println("."); 
		int len=given.length();
		double userID=-1;
		int finID=-1;
		if(given.substring(0,3).equals("515") && given.substring(len-3,len).equals("151")) {
			userID=Double.parseDouble(given.substring(3, len-3));
			finID=(int)Math.sqrt(userID);
			if(valid_user(finID)<=0){
				System.err.println("unauthorized connection between webservices and microservices");
				userID=-1;
			}
		}
		else {
			System.err.println("unauthorized connection between webservices and microservices");
		}
		return finID;
	}
	public int valid_user(int userID) {
		
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		int count=0;
		try {
			con = obj.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from users where user_id="+userID);
			
			while (rs.next()) {
				count++;
			}
			

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return count;

		
	}
}
