package com.watchcar.beans;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class BeanSocket {
	private String address;
	private Socket soc;
	private PrintWriter outs;
	
	public BeanSocket(String addr) throws UnknownHostException, IOException{
		this.address=addr;
		this.soc = new Socket(addr,6020);
		this.outs = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true) ;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Socket getSoc() {
		return soc;
	}
	public void setSoc(Socket soc) {
		this.soc = soc;
	}
	public PrintWriter getOuts() {
		return outs;
	}
	public void setOuts(PrintWriter outs) {
		this.outs = outs;
	}

}
