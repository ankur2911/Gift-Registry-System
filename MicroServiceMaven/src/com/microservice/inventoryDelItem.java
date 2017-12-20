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

@Path("/inventoryDelItem")
public class inventoryDelItem {
	
	
	@POST
	@Path("{param1}/{param2}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") int item_code) {
		System.out.println("      ------------------------------");
		System.out.println("      inside delete_inventory_item micro service");
		
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
			    String sql = "DELETE FROM GIFT_REGISTRY " +
			                   "WHERE item_code ="+item_code;
			    stmt.executeUpdate(sql);
			    String sq2 = "DELETE FROM ITEMS " +
		                   "WHERE item_code ="+item_code;
			    stmt.executeUpdate(sq2);
			    System.out.println("      delete successfull");
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
