package example4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener{
		//필드 
		JTextField text_send;
		
		//생성자
		public ClientFrame(String title) {
			super(title);
			//프레임의 레이아웃 법칙 지정하기
			setLayout(new BorderLayout());
			//상단 페널
			JPanel topPanel=new JPanel();
			topPanel.setBackground(Color.blue);
			//페널을 상단에 배치하기 
			add(topPanel, BorderLayout.NORTH);
	
			//아래 메소드에서 필요한값을 필드에 저장하기 
			text_send=new JTextField(20);
			
			JButton btn_send=new JButton("send");
			//페널에 순서대로 추가하기
			topPanel.add(text_send);
			topPanel.add(btn_send);
			//버튼에 Action command 지정
			btn_send.setActionCommand("send");
			//버튼에 리스너 등록
			btn_send.addActionListener(this);
		}
			

		//메인 메소드
		public static void main(String[] args) {
			//프레임
			ClientFrame f=new ClientFrame("chatting");
			//프레임의 x, y, width, height 설정
			f.setBounds(100, 100, 800, 500);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			
		
	}

		@Override
		public void actionPerformed(ActionEvent arg) {
			//JTextField 에 입력한 문자열 읽어와서
			String msg=text_send.getText();
			//Socket(접속 전송
			Socket socket=null;
			
			try {
				socket=new Socket("14.63.164.99",5000);
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
