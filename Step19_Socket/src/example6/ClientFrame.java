package example6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;
/*
 * - 대화방에 현재 누가 참여하고 있는지 목록 출력하기
 * - 누가 새로 입장했는지 메세지 출력하기
 * - 누가 퇴장했는지 메세지 출력하기
 * 
 *서버에 전달되는 메세지의 종류 
 * 1. 일반 대화 메세지
 * 	{"type":"msg","name":"김구라","content":"안녕하세요"}
 * 2. 누군가 입장했다는 메세지
 * 	{"type":"enter","name":"해골"}
 * 3. 누군가 퇴장했다는 메세지
 * 	{"type":"out","name":"원숭이"}
 * 4. 참여자 목록 메세지
 * 	{"type":"members","list":["주뎅이","덩어리","개다리"]}
 * 
 */
public class ClientFrame extends JFrame implements ActionListener, KeyListener{
		//필드 
		JTextField tf;
		Socket socket;
		BufferedWriter bw;
		BufferedReader br;
		JTextArea ta;
		String chatName; //대화명저장할 필드
		JList<String> jList;
		
		//생성자
		public ClientFrame(String title) {
			super(title);
			//프레임의 레이아웃 법칙 지정하기
			setLayout(new BorderLayout());
			//상단 페널
			JPanel topPanel=new JPanel();
			topPanel.setBackground(Color.blue);
			//페널을 상단에 배치하기 
			add(topPanel, BorderLayout.SOUTH);
	
			//아래 메소드에서 필요한값을 필드에 저장하기 
			tf=new JTextField(20);
			
			JButton btn_send=new JButton("send");
			//페널에 순서대로 추가하기
			topPanel.add(tf);
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
			tf.addKeyListener(this);
			
			//참여자 목록을 출력할 준비
			jList=new JList<>();
			jList.setBackground(Color.green);
			
			
			JPanel rightPanel=new JPanel();
			rightPanel.add(jList);
			add(rightPanel,BorderLayout.EAST);
			
			Vector<String> enterList=new Vector<>();
			enterList.add("참여자 목록");
			enterList.add("김구라");
			enterList.add("해골");
			
			
			
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
				chatName="오옷";
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
				//내가 입장한다고 서버에 메세지를 보낸다.
				//JSONObject 에 정보를 담고 
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("type", "enter");
				jsonObj.put("name", chatName);
				//JSONObject 에 담긴정보를 JSON 문자열로 만들어서 
				String msg=jsonObj.toString();
				//서버에 출력하기 
				bw.write(msg);
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg) {
			sendMessage();
		}
		//서버에서 문자열이 전송되는지 지속적으로 대기하는 스레드 객체를 생성할 클래스설계
		class ClientThread extends Thread{
			@Override
			public void run() {
				while(true) {
					try {
						//대기하다가 문자열이 도착하면 메소드가 리턴한다
						String line=br.readLine();
						//JSON 문자열을 이용해서 JSONObject 객체생성
						JSONObject jsonObj=new JSONObject(line);
						//어떤종류인지 읽어와본다
						String type=jsonObj.getString("type");
						if(type.equals("enter")) {
							String name=jsonObj.getString("name");
							ta.append("[ "+name+" ]님이 입장하셨습니다");
							ta.append("\r\n");
						}else if(type.equals("out")) {
							String name=jsonObj.getString("name");
							ta.append("[ "+name+" ]님이 퇴장하셨습니다");
						}else if(type.equals("msg")) {
							String name=jsonObj.getString("name");
							String content=jsonObj.getString("content");
							ta.append(name+" : "+content);
							ta.append("\r\n");
						}else if(type.equals("members")){
							//"list" 라는 키값으로 저장된 JSONArray 객체얻어오기
							JSONArray jsonArr=jsonObj.getJSONArray("list");
							//참여자 목록을 저장할 Vector
							Vector<String> list=new Vector<>();
							for(int i=0; i<jsonArr.length(); i++) {
								//JSONArray 에서 i번째 참여자 명단을 얻어와서
								String tmp=jsonArr.getString(i);
								//Vector 에 누적시키기
								list.add(tmp);
							}
							//반복문 돌고 난후 참여자 목록 Vector 를 JList 에 연결하기
							jList.setListData(list);
						}else if(type.equals("out")) {
							String name=jsonObj.getString("name");
							ta.append("# "+name+" # 님이 되장했습니다.");
							ta.append("\r\n");
						}
						
						if(line==null) {//서버와 접속이 끊기면
							break; //while 문탈출
						}
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
				sendMessage();
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
		//메세지를 전송하는 메소드
		public void sendMessage() {
			//전송할 문자열
			String msg=tf.getText();
			try {
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("type", "msg");
				jsonObj.put("name", chatName);
				jsonObj.put("content", msg);
				String json=jsonObj.toString();
				bw.write(json);
				bw.newLine();
				bw.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}
			tf.setText("");
		}
}//class ClientFrame
