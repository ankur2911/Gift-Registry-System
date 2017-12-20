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

@Path("/GetVisible")
public class GetVisible {

	@POST
	@Path("{param1}")
	public String visible(@PathParam("param1") String userID_e) {
		// share=0 is visible to no one
		// share=1 is visible to specified people- go to visible table
		// share=2 is visible to everyone
		System.out.println("       ------------------------------");
		System.out.println("       inside getVisible micro service");
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		if(userID!=-1) {
			try {
				con = obj.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select username, user_registry.registry_id, "+
				     "count(item_code) from user_registry, gift_registry, users where "+
					 "gift_registry.registry_id=user_registry.registry_id and users.user_id=user_registry.user_id "+
				     "and share_status=2 and username<>'admin' and user_registry.user_id <>'"+userID+"'" +
					 " group by registry_id");
	
				while (rs.next()) {
					ArrayList registry = new ArrayList(); 
					registry.add(rs.getString("username"));
					registry.add(rs.getInt("registry_id"));
					registry.add(rs.getInt("count(item_code)"));
					send.add(registry);
				}
				Statement st1 = con.createStatement();
				ResultSet rs1 = st1.executeQuery("select username, visible.registry_id, count(item_code) "+
						"from visible, gift_registry, users where gift_registry.registry_id=visible.registry_id and " +
					     "users.user_id=gift_registry.user_id and visible.user_id='"+userID+"' group by visible.registry_id");
				while (rs1.next()) {
					ArrayList registry = new ArrayList();
					registry.add(rs1.getString("username"));
					registry.add(rs1.getInt("registry_id"));
					registry.add(rs1.getInt("count(item_code)"));
					send.add(registry);
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
		myObj.add("visible", final_send);
		System.out.println("     visible="+myObj.toString());
		System.out.println("     ------------------------------");
		return myObj.toString();
	}

	public int get_count(Connection con, int id) throws SQLException {

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select count(*) from gift_registry where registry_id='" + id + "'");
		int count = 0;
		while (rs.next()) {
			count = rs.getInt("count(*)");
		}
		return count;
	}

}
