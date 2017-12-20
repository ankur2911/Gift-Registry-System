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

@Path("/addRegistry")
public class addRegistry {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") int shareStatus, @PathParam("param3") String shareList) {
		System.out.println("------------------------------");
		System.out.println("inside addRegistry micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int registry_id=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();
				
				registry_id=gen_registryID(con);
				System.out.println("registryid "+ registry_id);
				String query = "INSERT INTO USER_REGISTRY(USER_ID, REGISTRY_ID, SHARE_STATUS)"
	                    + " values (?, ?, ?)";
	            PreparedStatement preparedStmt = con.prepareStatement(query);
	            preparedStmt.setInt(1,userID );
	            preparedStmt.setInt(2, registry_id);
	            preparedStmt.setInt(3, shareStatus);
	            preparedStmt.execute();
	            
	            if(shareStatus==1) {
	            	String list[]=shareList.split(",");
	            	for(int i=0; i<list.length; i++) {
	            		String query1 = "INSERT INTO VISIBLE(REGISTRY_ID, USER_ID)"
			                    + " values (?, ?)";
			            PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			            preparedStmt1.setInt(1,registry_id );
			            preparedStmt1.setInt(2, Integer.parseInt(list[i]));
			            preparedStmt1.execute();
	            	}
	            }
	            send.add(0, registry_id);
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
		return Integer.toString(registry_id);
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
