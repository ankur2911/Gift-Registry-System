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

@Path("/search")
public class search {

	@POST
	@Path("{param1}/{param2}")
	public String userauth(@PathParam("param1") String userID_e, @PathParam("param2") String search) {
		System.out.println("     ------------------------------");
		System.out.println("     inside search micro service");

		ValidUser v_user = new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		System.out.println("90909090----" + userID_e);
		int userID = v_user.checkEncryption(userID_e);
		if (userID != -1) {
			try {
				con = obj.getConnection();
				Statement stmt = con.createStatement();
				System.out.println("hellooo"+search);
				if (search.charAt(0) == '+') {
					search=search.substring(2, search.length());
					search=search.replace("+"," ");
					System.out.println("quotes"+search);
					send = executeSearch(search, con);
				} else {
					String[] data = search.split("\\+");
					for (int i = 0; i < data.length; i++) {
						System.out.println("darr "+ data[i]);
						String q1 = "(SELECT * FROM ITEMS WHERE item_code " + "LIKE '%" + data[i] + "%')";
						String q2 = "(SELECT * FROM ITEMS WHERE name " + "LIKE '%" + data[i] + "%')";
						String q3 = "(SELECT * FROM ITEMS WHERE cost " + "LIKE '%" + data[i] + "%')";
						String q4 = "(SELECT * FROM ITEMS WHERE type " + "LIKE '%" + data[i] + "%')";
						String q5 = "(SELECT * FROM ITEMS WHERE description " + "LIKE '%" + data[i] + "%')";
						String query = q1 + " UNION " + q2 + " UNION " + q3+ " UNION " + q4 + " UNION " + q5;
						PreparedStatement statement = con.prepareStatement(query);
						ResultSet rs = statement.executeQuery();
						System.out.println("dfdfdeeeeeeeeeeee");
						while (rs.next()) {
							inventory_items item = new inventory_items();
							item.setItem_code(rs.getInt("item_code"));
							item.setName(rs.getString("name").trim());
							item.setCost(rs.getDouble("cost"));
							item.setType(rs.getString("type"));
							item.setDescription(rs.getString("description"));
							send.add(item);
						}
					}
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
		System.out.println("      ------------------------------");
		return myObj.toString();
	}
	public ArrayList executeSearch(String data, Connection con) throws SQLException {
		System.out.println("data sent" + data);
		
		String q1 = "(SELECT * FROM ITEMS WHERE item_code " + "LIKE '%" + data + "%')";
		String q2 = "(SELECT * FROM ITEMS WHERE name " + "LIKE '%" + data + "%')";
		String q3 = "(SELECT * FROM ITEMS WHERE cost " + "LIKE '%" + data + "%')";
		String q4 = "(SELECT * FROM ITEMS WHERE type " + "LIKE '%" + data + "%')";
		String q5 = "(SELECT * FROM ITEMS WHERE description " + "LIKE '%" + data + "%')";
		String query = q1 + " UNION " + q2 + " UNION " + q3+ " UNION " + q4 + " UNION " + q5;
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		ArrayList send1 = new ArrayList();
		while (rs.next()) {
			inventory_items item = new inventory_items();
			item.setItem_code(rs.getInt("item_code"));
			item.setName(rs.getString("name").trim());
			item.setCost(rs.getDouble("cost"));
			item.setType(rs.getString("type"));
			item.setDescription(rs.getString("description"));
			send1.add(item);
		}
		return send1;
	}

}
