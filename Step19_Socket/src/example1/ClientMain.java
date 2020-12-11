package example1;

import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) {
		Socket socket=null;
		try {
			socket=new Socket("127.0.0.1",5000);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
