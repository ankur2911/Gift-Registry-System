package com.microservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/addUserDetails")
public class addUserDetails {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}/{param4}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String email, @PathParam("param3") int question,
			@PathParam("param4") String answer) {
		System.err.println("      inside addUSERDETAILS micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();	
				
		         String query1 = "INSERT INTO USER_INFO(USER_ID,EMAIL, QUESTION, ANSWER)"
		                    + " values (?, ?, ?, ?)";
	            PreparedStatement preparedStmt1 = con.prepareStatement(query1);
	            preparedStmt1.setInt(1,userID);
	            preparedStmt1.setString(2, email);
	            preparedStmt1.setInt(3, question);
	            preparedStmt1.setString(4, answer);
	            preparedStmt1.execute();
	            success=1;
			    System.out.println("      add successful");
				
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
		}
		System.out.println("      ------------------------------");
		return Integer.toString(success);
	}
	public int ifUnique(Connection con, String username) throws SQLException {
		PreparedStatement p=con.prepareStatement(
				"SELECT COUNT(*) FROM USERS WHERE USERNAME='"+username+"'");
		ResultSet rs= p.executeQuery();
		rs.next();
		System.out.println("dfdfd"+rs.getInt(1));
		return rs.getInt(1);
		
	}

}

