package com.microservice;

import java.sql.*;
import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pojo.gift_list;
import com.pojo.inventory_items;

import connection.ConnectionClass;
import connection.ValidUser;

@Path("/InventoryItem")
public class InventoryItem {

	@POST
	@Path("{param1}")
	public String userauth(@PathParam("param1") String userID_e) {
		System.err.println("     inside inventory_items micro service");

		ValidUser v_user = new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID = v_user.checkEncryption(userID_e);
		if (userID != -1) {
			try {
				con = obj.getConnection();
				String q1 = "(SELECT * FROM ITEMS)";
				PreparedStatement statement = con.prepareStatement(q1);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					inventory_items item = new inventory_items();
					item.setItem_code(rs.getInt("item_code"));
					item.setName(rs.getString("name").trim());
					item.setCost(rs.getDouble("cost"));
					item.setType(rs.getString("type"));
					item.setDescription(rs.getString("description"));
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
		} else {
			send.add(0, -2);
		}
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		JsonElement final_send = gson.toJsonTree(send);
		myObj.add("result", final_send);
		System.out.println("      items=" + myObj.toString());
		return myObj.toString();
	}


}
