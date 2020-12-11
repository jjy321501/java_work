package example2;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
	public static void main(String[] args) {
		//1. Socket 을 통해서 출력할 문자열을 입력받아서
		Scanner sc=new Scanner(System.in);
		System.out.println("전송할 문자열 입력:");
		String msg=sc.nextLine();
		Socket socket=null;
		try {
			socket=new Socket("127.0.0.1",6000);
			System.out.println("서버에 Socket 접속 성공");
			//2. Socket 을 통해서 출력하기
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(os);
			osw.write(msg);//문자열입력
			osw.write("\r\n");//개행기호입력
			osw.flush();//방출
			osw.close();//닫아주기
			socket.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
