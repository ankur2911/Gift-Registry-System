package com.validateRequest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.whalin.MemCached.SockIOPool;

/**
 * Servlet implementation class Initial
 */
@WebServlet(name = "InitialCache", urlPatterns = "/initialCache", loadOnStartup = 0)
public class initialCacheLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public initialCacheLoad() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException {
    	String[] servers = {"localhost:11211"};
		SockIOPool pool = SockIOPool.getInstance("Test2");
		pool.setServers( servers );
		pool.setFailover( true );
		pool.setInitConn( 10 );
		pool.setMinConn( 5 );
		pool.setMaxConn( 250 );
		pool.setMaintSleep( 30 );
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setAliveCheck( true );
		pool.initialize();
    }
	
	

}
