import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {

    public static void main(String[] args) {
	    	try {
	    	// Init server
	    	ServerSocket serv_soc;
			serv_soc = new ServerSocket(6020);
			Socket soc = serv_soc.accept();
			
			BufferedReader ins = new BufferedReader(new	InputStreamReader(soc.getInputStream()));
		
			// Init serial port
			SerialPort serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
			serialPort.openPort();
			//Open serial port
	        serialPort.setParams(SerialPort.BAUDRATE_115200, 
	                             SerialPort.DATABITS_8,
	                             SerialPort.STOPBITS_1,
	                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
	        
	        int i = 0;
	        while (i < 1) {
	        	String request = ins.readLine();
	        	System.out.println("Request :"+request);
	        	if (request != null) {
		        	if (request.equals("left")) {
		        		serialPort.writeBytes("4".getBytes());//Write data to port
		        	}
		        	else if (request.equals("right")) {
		        		serialPort.writeBytes("6".getBytes());
		        	}
		        	else if (request.equals("stop")) {
		        		serialPort.writeBytes("5".getBytes());
		        	}
		        	else if (request.equals("forward")) {
		        		serialPort.writeBytes("8".getBytes());
		        	}
		        	else if (request.equals("backward")) {
		        		serialPort.writeBytes("2".getBytes());
		        	}
		        	else if (request.equals("drift back right")) {
		        		serialPort.writeBytes("3".getBytes());
		        	}
		        	else if (request.equals("drift back left")) {
		        		serialPort.writeBytes("1".getBytes());
		        	}
		        	else if(request.equals("drift left")) {
		        		serialPort.writeBytes("7".getBytes());
		        	}
		        	else if(request.equals("drift right")) {
		        		serialPort.writeBytes("9".getBytes());
		        	}
		        	else if(request.equals("rotate left")) {
		        		serialPort.writeBytes("0".getBytes());
		        	}
		        	else if(request.equals("rotate right")) {
		        		serialPort.writeBytes(".".getBytes());
		        	}
		        	else {
		        		System.out.println("Unknown command");
		        	}
	        	}
	        	else {
	        		i++;
	        	}
			}
	        
	        serialPort.closePort();
	        ins.close();
	        soc.close();
	    }
        catch (SerialPortException e) {
        	System.out.println("Error while opening the serial port.");
		}
        catch (IOException e) {
			System.out.println("Error input/output while initializing the server");
		}
    }
}