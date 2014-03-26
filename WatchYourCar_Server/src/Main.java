import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {

    public static void main(String[] args) {
    	ServerSocket serv_soc = null;
    	
    	ExecutorService es;
		es = Executors.newFixedThreadPool(1);
		
		// Init serial port
		SerialPort serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_115200, 
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			
		    // Init server
			serv_soc = new ServerSocket(6020);
			while (true) {
				Socket soc = serv_soc.accept();
				es.execute(new Connection(soc,serialPort));
			}
		}
        catch (SerialPortException e) {
        	e.printStackTrace();
        	System.out.println("Error while opening the serial port.");
		}
        catch (IOException e) {
        	e.printStackTrace();
			System.out.println("Error input/output while initializing the server (Port 6020 may already be in use)");
		}
		finally {
			try {
				serialPort.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
				System.out.println("Error while closing the serial port.");
			}
			try {
				serv_soc.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("The socket has not been able to close.");
			}
		}
    }
}