package com.microservice;

import java.sql.*;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/AddItem")
public class AddItem {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") int registry_id, @PathParam("param3") int item_code) {
		System.out.println("      ------------------------------");
		System.out.println("      inside add_item micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();	
				
				System.out.println("registryid "+ registry_id);
				String query = "INSERT INTO GIFT_REGISTRY(USER_ID, ITEM_CODE, REGISTRY_ID)"
	                    + " values (?, ?, ?)";
	            PreparedStatement preparedStmt = con.prepareStatement(query);
	            preparedStmt.setInt(1,userID );
	            preparedStmt.setInt(2, item_code);
	            preparedStmt.setInt(3, registry_id);
	            preparedStmt.execute();
			    System.out.println("      add successful");
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
