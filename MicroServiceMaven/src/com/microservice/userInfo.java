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

@Path("/userInfo")
public class userInfo {

	@POST
	@Path("{param1}")
	public String userauth(@PathParam("param1") String userID_e) {
		System.err.println("     inside get_userInfo micro service");

		ValidUser v_user = new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID = v_user.checkEncryption(userID_e);
		if (userID != -1) {
			try {
				con = obj.getConnection();
				String q1 = "(SELECT EMAIL,ANSWER FROM USER_INFO where user_id ="+userID+")";
				PreparedStatement statement = con.prepareStatement(q1);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					ArrayList user = new ArrayList(); 
					user.add(rs.getString("email"));
					user.add(rs.getString("answer"));
					send.add(user);
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
