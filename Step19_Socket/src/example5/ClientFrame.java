package example5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener, KeyListener{
		//필드 
		JTextField text_send;
		Socket socket;
		BufferedWriter bw;
		BufferedReader br;
		JTextArea ta;
		private String chatName; //대화명저장할 필드
		
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
			ta=new JTextArea();
			JScrollPane scPane=new JScrollPane(ta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			//스크롤 가능한 UI를 프레임의 중앙에 배치
			add(scPane,BorderLayout.CENTER);
			//오직 출력용도로만
			ta.setEditable(false);
			//JTextField 에 KeyListener 등록하기
			text_send.addKeyListener(this);
			//소켓접속하기
			connect();
			
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
		//Socket 서버에 접속하는 메소드
		public void connect() {
			//대화명 입력받아 필드에 저장하기
			chatName=JOptionPane.showInputDialog(this, "대화명을 입력하세요");
			if(chatName==null || chatName.equals("")) {
				chatName="나천재";
			}
			
			try {
				//소켓객체
				socket=new Socket("14.63.164.99",5000);
				//서버에 문자열을 출력할 객체
				bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				//서버가 전송하는 문자열을 읽어들일 객체
				br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//새로운 스레드를 시작시켜서 서버에서 문자열이 도착하는지 지속적으로 대기한다.
				new ClientThread().start();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg) {
			//JTextField 에 입력한 문자열 읽어와서
			String msg=text_send.getText();
			//BufferedWriter 객체를 이용해서 출력한다
			try {
				bw.write(chatName+" : "+msg);
				bw.newLine();
				bw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//3.JTextField 에 입력한 문자열 삭제
			text_send.setText("");	
		}
		//서버에서 문자열이 전송되는지 지속적으로 대기하는 스레드 객체를 생성할 클래스설계
		class ClientThread extends Thread{
			@Override
			public void run() {
				while(true) {
					try {
						//대기하다가 문자열이 도착하면 메소드가 리턴한다
						String line=br.readLine();
						//도착된 메시지를 JTextArea에 개행기호와 함께 추가
						ta.append(line+"\r\n");
						//출력할 문서의 높이
						int height=ta.getDocument().getLength();
						//높이만큼 JTextArea 를 스크롤 시켜서 가장아래에 있는 문자열이 보이게
						ta.setCaretPosition(height);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			//만일 엔터키를 눌렀다면 
			if(e.getKeyCode()== KeyEvent.VK_ENTER) {
				//JTextField 에 입력한 문자열 읽어와서
				String msg=text_send.getText();
				//BufferedWriter 객체를 이용해서 출력한다
				try {
					bw.write(chatName+" : "+msg);
					bw.newLine();
					bw.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//3.JTextField 에 입력한 문자열 삭제
				text_send.setText("");	
			}
			
		}


		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
}
