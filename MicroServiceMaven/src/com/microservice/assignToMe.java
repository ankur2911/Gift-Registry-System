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

@Path("/assignToMe")
public class assignToMe {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}/{param4}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String username, @PathParam("param3") String registry_id,  @PathParam("param4") String item_code) {
		
		System.err.println("   inside assign to me MICRO service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				System.out.println(username+"  "+ registry_id+"  "+ item_code);
				con = obj.getConnection();
				Statement stmt = con.createStatement();
			    String sql = "UPDATE GIFT_REGISTRY " +
			                   "SET ASSIGNED_USER ='"+username +"' WHERE registry_id="+registry_id+" and item_code="+item_code;
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
		return Integer.toString(success);
	}
	public int gen_registryID(Connection con) throws SQLException {
		Random rand = new Random();
		int  id = rand.nextInt(1000) + 1;
		int check= check_ID(con, id);
		System.out.println("id="+id+" check="+ check);
		if(check!=0) {
			id= gen_registryID(con);
		}
		return id;
	}
	public int check_ID(Connection con, int id) throws SQLException {
		PreparedStatement p=con.prepareStatement(
				"SELECT COUNT(*) FROM USER_REGISTRY WHERE REGISTRY_ID IN ('" + id +"')");
		ResultSet rs= p.executeQuery();
		rs.next();
		return rs.getInt(1);
		
	}

}

