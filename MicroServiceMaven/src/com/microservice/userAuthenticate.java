package com.microservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import connection.ConnectionClass;

@Path("/userauth")
public class userAuthenticate {

	@POST
	@Path("{param1}/{param2}")
	public String userauth(@PathParam("param1") String param1, @PathParam("param2") String param2) {

		System.out.println("inside userauth micro service");
		String user = param1;
		String pass = param2;
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		String validated = "no";
		int count = -1;
		try {
			con = obj.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from users where username='" + user + "' and password='" + pass + "'");
			
			while (rs.next()) {
				count=rs.getInt("user_id");
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

		//

		return Integer.toString(count);
	}

}
