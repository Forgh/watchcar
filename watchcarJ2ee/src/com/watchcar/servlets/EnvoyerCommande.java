package com.watchcar.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watchcar.beans.BeanSocket;


public class EnvoyerCommande extends HttpServlet {

	private static final long serialVersionUID = -8181806415956821939L;
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
	    BeanSocket bean = (BeanSocket) getServletContext().getAttribute("beanSocket");
		
	    String commande = request.getParameter("id");
	    System.out.println(commande);
		bean.getOuts().println(commande);
		
	    if(commande.equals("close")){
	    	bean.getOuts().close();
			try {
				bean.getSoc().close();
			} catch (IOException e) {
				
			}
	    }
			
	}
	/*
	public void destroy(){
	    BeanSocket bean = (BeanSocket) getServletContext().getAttribute("beanSocket");

		bean.getOuts().close();
		try {
			bean.getSoc().close();
		} catch (IOException e) {
			
		}
		
	}*/
}
