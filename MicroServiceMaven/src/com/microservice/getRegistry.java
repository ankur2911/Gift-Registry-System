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

@Path("/getRegistry")
public class getRegistry {

	@POST
	@Path("{param1}")
	public String visible(@PathParam("param1") String userID_e) {
		System.out.println("     ------------------------------");
		System.out.println("     inside getRegistry micro service"); 
		
		ValidUser v_user=new ValidUser();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		ArrayList send = new ArrayList();
		int userID=v_user.checkEncryption(userID_e);
		if(userID!=-1) {
			try {
				con = obj.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select registry_id from user_registry where user_id="+userID);
	
				while (rs.next()) {
					send.add(rs.getInt("registry_id"));
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
			send.add(0,-2);
		}

		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		JsonElement final_send = gson.toJsonTree(send);
		myObj.add("registry", final_send);
		System.out.println("     registry="+myObj.toString());
		System.out.println("     ------------------------------");
		return myObj.toString();
	}

}

