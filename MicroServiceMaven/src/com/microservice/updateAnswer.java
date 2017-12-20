package com.microservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/updateAnswer")
public class updateAnswer {
	
	
	@POST
	@Path("{param1}/{param2}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String answer) {
		System.err.println("      inside update answer micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();
				Statement stmt = con.createStatement();
			    String sql = "UPDATE USER_INFO SET answer='"+answer+"' "+
			                   "WHERE user_id ="+userID;
			    stmt.executeUpdate(sql);
	            success=1;
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

}

