package com.microservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/registerUser")
public class registerUser {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String username, @PathParam("param3") String password) {
		System.err.println("      inside registerUSer micro service");
		 
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int genID=-2;
		if(userID!=-1) {
			try {
				System.out.println(username+ " "+ password);
				con = obj.getConnection();	
				int unique=ifUnique(con, username);
				if(unique==0) {
					String query = "INSERT INTO USERS(USERNAME, PASSWORD)"
		                    + " values (?, ?)";
		            PreparedStatement preparedStmt = con.prepareStatement(query);
		            preparedStmt.setString(1,username );
		            preparedStmt.setString(2, password);
		            preparedStmt.execute();
		            
		            
		            Statement st = con.createStatement();
		            ResultSet rs = st.executeQuery("select user_ID from users where username='"+username+"'");
		            while (rs.next()) {
		            	genID=rs.getInt("user_ID");
		            }
				}
				else {
					genID=-1;
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
		}
		System.out.println("      ------------------------------");
		return Integer.toString(genID);
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

