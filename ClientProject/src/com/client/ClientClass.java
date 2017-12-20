package com.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.Client;
/**
 * Servlet implementation class Client
 */
@WebServlet("/ClientClass")
public class ClientClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientClass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Client client = Client.create();
		HttpSession session=request.getSession();
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		System.out.println("before first service call : "+ username);
		WebResource webresource= client.resource("https://localhost:8444/WebServiceMaven/backend/Login/"+username+"/"+password);
		System.out.println("after webservice");
		ClientResponse rep = webresource.accept("text/html").post(ClientResponse.class);
		if(rep.getStatus()==200) {
			String output=rep.getEntity(String.class);
			System.out.println("success: "+output);
			if(output.contains("[-1")) {
				session.setAttribute("invalid", "invalid");
            	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
			}else {
				session.setAttribute("nameobj", output);
				if(username.equals("admin")){
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AdminPage.jsp");
		            dispatcher.forward(request, response);
				}
				else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
		            dispatcher.forward(request, response);
				}	
			}
		}
	}

}
