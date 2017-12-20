package com.microservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/forgotPassword")
public class forgotPassword {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}/{param4}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String email, @PathParam("param3") int question,
			@PathParam("param4") String answer) {
		System.err.println("      inside forgotPassword micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();	
				String password="";
				Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery("select password from users, user_info where users.user_id=user_info.user_id "+
				     "and email='"+email+"' and question="+question+" and answer='"+answer+"'");
	            while (rs.next()) {
	            	password=rs.getString("password");
	            }
	            success=1;
	            send.add(password);
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
		else {
			send.add(0, -2);
		}
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		JsonElement final_send = gson.toJsonTree(send);
		myObj.add("result", final_send);
		System.out.println("      items=" + myObj.toString());
		System.out.println("      ------------------------------");
		return myObj.toString();
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

