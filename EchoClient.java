import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.util.Scanner;

public
class EchoClient {
    public static void main(String[] arstring) {
    	    String keystore = "mySrvKeystore";
	char keystorepass[] = "123456".toCharArray();
	char keypassword[] = "123456".toCharArray();
	Scanner keyIn = new Scanner(System.in);
        try {
        	KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(keystore), keystorepass);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, keypassword);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(ks);
		SSLContext sslcontext = SSLContext.getInstance("SSLv3");
		sslcontext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory)sslcontext.getSocketFactory();
		SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 1379);
            //SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            //SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("10.0.0.2", 1379);
			
            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			
            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
			
            String string = null;
           /* while ((string = bufferedreader.readLine()) != null) {
                bufferedwriter.write(string + '\n');
                bufferedwriter.flush();
                
            }*/
            boolean connected = true;
			while(connected){
				bufferedwriter.write(keyIn.nextLine()+'\n');
				bufferedwriter.flush();
				String input = bufferedreader.readLine();
				if(input != null){
					System.out.println(input);
				}else connected=false;
			}
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}