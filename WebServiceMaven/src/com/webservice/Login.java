package com.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.HashMap;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

@Path("/Login")
public class Login {

	@POST
	@Path("{param1}/{param2}")
	@Produces(MediaType.TEXT_HTML)
	public String loginUser(@PathParam("param1") String param1, @PathParam("param2") String param2,@Context HttpServletResponse response) throws IOException {
//			System.out.println("sdfd "+ user);
//		 	String param1 = request.getParameter("username");
//			String param2 = request.getParameter("password");
	        response.setContentType("text/html");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");

	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type, origin, authorization, accept, client-security-token");
	        response.setHeader("Access-Control-Max-Age", "86400");
	        
	    ArrayList send = new ArrayList();
	    System.out.println("*************************************************");
		System.out.println("inside login WEBservice");	
		
		// call to microservice to authenticate user credentials:
		
		Client client = Client.create();
	

		WebResource webresource= client.resource("https://localhost:8444/MicroServiceMaven/backend/userauth/"+param1+"/"+param2);
		ClientResponse rep = webresource.accept("text/html").post(ClientResponse.class);
		if(rep.getStatus()==200) {
			int output=Integer.parseInt(rep.getEntity(String.class));
			String output_encrypt="515"+(int)Math.pow(output, 2)+"151";
			if(output>0 && output!=4) {
				send.add(0, output);
				send.add(1,param1);
				System.out.println("dfdfdfdfdf");
//				WebResource webresource1= client.resource("http://localhost:8080/MicroServiceMaven/backend/UserItems/"+output_encrypt);
//				ClientResponse rep1 = webresource1.accept("text/html").post(ClientResponse.class);
//				if(rep1.getStatus()==200) {
//					String items=rep1.getEntity(String.class);
//					System.out.println(items);
//					send.add(items);
//				}
				WebResource webresource2= client.resource("https://localhost:8444/MicroServiceMaven/backend/GetVisible/"+output_encrypt);
				ClientResponse rep2 = webresource2.accept("text/html").post(ClientResponse.class);
				if(rep2.getStatus()==200) {
					String visible=rep2.getEntity(String.class);
					send.add(visible);
				}
				WebResource webresource3= client.resource("https://localhost:8444/MicroServiceMaven/backend/UsersList/"+output_encrypt);
				ClientResponse rep3 = webresource3.accept("text/html").post(ClientResponse.class);
				if(rep3.getStatus()==200) {
					String visible=rep3.getEntity(String.class);
					send.add(visible);
				}
			}
			else {
				if(output==4) {
					send.add(0, output);
					send.add(1,param1);
				}
				else {
					send.add(0, -1);
				}
			}
		}
		Gson gson = new Gson();
	    JsonObject myObj = new JsonObject();
	    JsonElement final_send = gson.toJsonTree(send);
	    myObj.add("result", final_send);
	    System.out.println("webserviceOUTPUT="+myObj.toString());
	    System.out.println("*************************************************");
	    return myObj.toString();
		
	}
	
}
