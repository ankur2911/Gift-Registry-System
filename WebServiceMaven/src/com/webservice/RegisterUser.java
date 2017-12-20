package com.webservice;

import java.io.IOException;
import java.util.ArrayList;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.validateRequest.ValidateR;
import com.whalin.MemCached.MemCachedClient;

@Path("/RegisterUser")
public class RegisterUser {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loginUser(@FormParam("userIDcheck") String userIDcheck,@FormParam("userID") int userID,@FormParam("username") String username,
			@FormParam("password") String password,	@FormParam("email") String email, @FormParam("question") int question,
			@FormParam("answer") String answer, @Context HttpServletResponse response) throws IOException {
	    
		System.out.println("inside Register WEBservice");
			System.out.println(answer+ " "+ question+ " "+ email);
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, origin, authorization, accept, client-security-token");
        response.setHeader("Access-Control-Max-Age", "86400");
	        
	    ArrayList send = new ArrayList();
		ValidateR v=new ValidateR();
		Client client = Client.create();
	
		if(v.validate(userIDcheck, userID)){
			String output_encrypt="515"+(int)Math.pow(4, 2)+"151";
			System.out.println("booo");
			WebResource webresource= client.resource("https://localhost:8444/MicroServiceMaven/backend/registerUser/"+output_encrypt+"/"+username+"/"+password);
			ClientResponse rep = webresource.accept("text/html").post(ClientResponse.class);
			if(rep.getStatus()==200) {
				int output=Integer.parseInt(rep.getEntity(String.class));
				System.out.println("output");
				if(output>0) {
					String output_encrypt1="515"+(int)Math.pow(output, 2)+"151";
					WebResource webresource1= client.resource("https://localhost:8444/MicroServiceMaven/backend/addUserDetails/"+output_encrypt1+"/"+email+"/"+question+"/"+answer);
					ClientResponse rep1 = webresource1.accept("text/html").post(ClientResponse.class);
					if(rep1.getStatus()==200) {
						int fin=Integer.parseInt(rep1.getEntity(String.class));
					}
					
				}
				send.add(output);
			}
		}
		else {
			send.add(0,-2);
		}
	
		Gson gson = new Gson();
	    JsonObject myObj = new JsonObject();
	    JsonElement final_send = gson.toJsonTree(send);
	    myObj.add("result", final_send);
	    System.out.println(myObj.toString());
	    System.out.println("*************************************************");
	    return myObj.toString();
		
	}
	
	
}
