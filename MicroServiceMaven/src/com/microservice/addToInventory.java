package com.microservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/addToInventory")
public class addToInventory {
	
	
	@POST
	@Path("{param1}/{param2}/{param3}/{param4}/{param5}")
	public String registry(@PathParam("param1") String userID_e, @PathParam("param2") String name, @PathParam("param3") String cost,
			@PathParam("param4") String type, @PathParam("param5") String description) {
		System.err.println("      inside add_into_inventory micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		int success=-2;
		if(userID!=-1) {
			try {
				con = obj.getConnection();	
				int item_code=getCode(con);
				String query = "INSERT INTO ITEMS(ITEM_CODE, NAME, COST, TYPE, DESCRIPTION)"
	                    + " values (?, ?, ?, ?, ?)";
	            PreparedStatement preparedStmt = con.prepareStatement(query);
	            preparedStmt.setInt(1,item_code );
	            preparedStmt.setString(2, name);
	            preparedStmt.setDouble(3, Double.parseDouble(cost));
	            preparedStmt.setString(4, type);
	            preparedStmt.setString(5, description);
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
	public int getCode(Connection con) throws SQLException {
		Random rand = new Random();
		int  id = rand.nextInt(1000) + 1;
		int check= check_code(con, id);
		System.out.println("id="+id+" check="+ check);
		if(check!=0) {
			id= getCode(con);
		}
		return id;
	}
	public int check_code(Connection con, int id) throws SQLException {
		PreparedStatement p=con.prepareStatement(
				"SELECT COUNT(*) FROM ITEMS WHERE ITEM_CODE IN ('" + id +"')");
		ResultSet rs= p.executeQuery();
		rs.next();
		return rs.getInt(1);
		
	}

}
