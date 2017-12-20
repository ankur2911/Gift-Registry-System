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
import com.whalin.MemCached.SockIOPool;

@Path("/RegistryItems")
public class RegistryItems {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loginUser(@FormParam("userIDcheck") String userIDcheck,@FormParam("userID") int userID,
			@FormParam("registry_id") int registry_id,@Context HttpServletResponse response) throws IOException {
	    
		System.out.println("*************************************************");
		System.out.println("inside RegistryItems WEBservice");
			
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
		MemCachedClient mcc = new MemCachedClient("Test2");
		//mcc.delete("1");
		//Get value from cache
		if(v.validate(userIDcheck, userID)){
			if(mcc.get(Integer.toString(registry_id))==null){
				System.out.println("cache miss="+registry_id);
				String output_encrypt="515"+(int)Math.pow(userID, 2)+"151";
				WebResource webresource= client.resource("https://localhost:8444/MicroServiceMaven/backend/UserItems/"+output_encrypt+"/"+registry_id);
				ClientResponse rep = webresource.accept("text/html").post(ClientResponse.class);
				if(rep.getStatus()==200) {
					String output=rep.getEntity(String.class);
					mcc.add(""+registry_id, output);
			
					send.add(output);
					System.out.println("add status:"+mcc.add(""+registry_id, output));
				}
			}else {
				System.out.println("cache hit="+registry_id);
				send.add(mcc.get(Integer.toString(registry_id)));
			}
			
			
		}
		else {
			send.add(0,-2);
		}
	
		Gson gson = new Gson();
	    JsonObject myObj = new JsonObject();
	    JsonElement final_send = gson.toJsonTree(send);
	    myObj.add("result", final_send);
	    System.out.println("items"+myObj.toString());
	    System.out.println("*************************************************");
	    return myObj.toString();
		
	}
	
}

