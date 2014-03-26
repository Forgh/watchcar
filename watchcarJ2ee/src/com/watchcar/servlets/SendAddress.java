package com.watchcar.servlets;

import java.io.IOException;




import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.watchcar.beans.BeanSocket;

public class SendAddress extends HttpServlet {
	
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Getting address and create the bean */
		String addr = request.getParameter("address");
		BeanSocket bean = new BeanSocket(addr);
		
		//Getting session then context and save the bean inside 
		HttpSession session = request.getSession(true);
        ServletContext context = session.getServletContext();
		context.setAttribute("beanSocket", bean);
		
		/* Transmission de la paire d'objets request/response à notre JSP */
        this.getServletContext().getRequestDispatcher("/WEB-INF/controlCommand.jsp").forward( request, response );
	}
}
