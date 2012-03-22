import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.util.Scanner;

class ProcessConnection extends Thread{
	Socket client;
	BufferedReader is;
	PrintWriter os;
	
	public ProcessConnection(Socket s){
		client = s;
		try{
			is=new BufferedReader(new InputStreamReader(client.getInputStream()));
			os=new PrintWriter(client.getOutputStream());
		}catch(IOException e){
			System.out.println("Exception: "+ e.getMessage());
		}
		this.start();
	}
	public void run(){
		try{
		//Scanner requestscanner = new Scanner(request);
		System.out.println("running");
		boolean connected = true;
		while(connected){
			
			String request = is.readLine();
			if(request.equalsIgnoreCase("Exit")) connected = false;
			else{
				System.out.println("request: " + request);
				os.println("Server Accepted: " + request.toUpperCase());
				os.flush();
			}
			}
		
		is.close();
		os.close();
		client.close();
		}catch(IOException e){
				e.printStackTrace();
			}
	}
}		
