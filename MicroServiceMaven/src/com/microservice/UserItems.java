package com.microservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pojo.gift_list;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/UserItems")
public class UserItems {

	@POST
	@Path("{param1}/{param2}")
	public String userauth(@PathParam("param1") String userID_e,@PathParam("param2") int registry_id) {
		System.out.println("     ------------------------------");
		System.out.println("     inside user_items micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		if(userID!=-1) {
			try {
				con = obj.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select items.item_code, name, cost, type,"
						+ "description, assigned_user from gift_registry,items where "
						+ "gift_registry.item_code=items.item_code and gift_registry.user_id='" + userID 
						+ "' and registry_id='"+registry_id+"'");
			
				while (rs.next()) {
					gift_list item = new gift_list();
					item.setItem_code(rs.getInt("item_code"));
					item.setName(rs.getString("name").trim());
					item.setCost(rs.getDouble("cost"));
					item.setType(rs.getString("type"));
					item.setDescription(rs.getString("description"));
					item.setAssigned_user(rs.getString("assigned_user"));
					send.add(item);
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
		else {
			send.add(0, -2);
		}

		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		JsonElement final_send = gson.toJsonTree(send);
		myObj.add("result", final_send);
		System.out.println("      items="+myObj.toString());
		System.out.println("      ------------------------------");
		return myObj.toString();
	}

}
