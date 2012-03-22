import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.net.ssl.*;
import java.security.*;

class ExampleServer{
	public static final int HTTPS_PORT = 1379;
	String keystore = "mySrvKeystore";
	char keystorepass[] = "123456".toCharArray();
	char keypassword[] = "123456".toCharArray();
	
	public ServerSocket getServer() throws Exception{
		//return new ServerSocket(HTTP_PORT);
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(keystore), keystorepass);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, keypassword);
		SSLContext sslcontext = SSLContext.getInstance("SSLv3");
		sslcontext.init(kmf.getKeyManagers(), null, null);
		SSLServerSocketFactory ssf = sslcontext.getServerSocketFactory();
		SSLServerSocket serversocket = (SSLServerSocket) ssf.createServerSocket(HTTPS_PORT);
		return serversocket;
	}
	
	public void run() {
		ServerSocket listen;
		try{
			listen = getServer();
			while(true){
				Socket client = listen.accept();
				ProcessConnection cc = new ProcessConnection(client);
				
			}
		}catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ExampleServer exserv = new ExampleServer();
		exserv.run();
	}
}
