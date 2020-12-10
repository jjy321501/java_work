package test.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import test.member.dao.MemberDao;
import test.member.dto.MemberDto;

public class MemberFrame extends JFrame implements ActionListener{
	JTextField text_name;
	JTextField text_addr;
	
	//생성자
	public MemberFrame(String title) {
		super(title);
		//프레임의 레이아웃 법칙 지정
		setLayout(new BorderLayout());
		//상단 패널
		JPanel topPanel=new JPanel();
		topPanel.setBackground(Color.yellow);
		add(topPanel,BorderLayout.NORTH);
		//패널에 추가할 UI 객체를 생성해서
		JLabel label_name=new JLabel("이름");
		JLabel label_addr=new JLabel("주소");
		//아래 메소드에서 필요한 값을 필드에 저장하기
		text_name=new JTextField(10);
		text_addr=new JTextField(10);
		JButton btn_add=new JButton("추가");
		//패널에 순서대로 추가하기
		topPanel.add(label_name);
		topPanel.add(text_name);
		topPanel.add(label_addr);
		topPanel.add(text_addr);
		topPanel.add(btn_add);
		//버튼에 Action command 지정
		btn_add.setActionCommand("add");
		//버튼에 리스터등록
		btn_add.addActionListener(this);
		
		//회원 목록을 출력할 테이블
		JTable table=new JTable();
		//칼럼명을 String[]에 순서대로 준비하기
		String[] colNames= {"번호","순서","주소"};
		//테이블에 연결할 기본 모델객체
		DefaultTableModel model=new DefaultTableModel(colNames,0);
		//모델을 테이블에 연결하기
		table.setModel(model);
		//테이블의 내용이 scroll될수 있도록 스크롤 패널로 감싼다.
		JScrollPane scPane=new JScrollPane(table);
		//스크롤 패널을 프레임 중앙에배치
		add(scPane, BorderLayout.CENTER);
		//모델의 메소드 확인해보기
		Object[] row1= {1,"김구라","노량진"};
		Object[] row2= {2,"해골","행신동"};
		model.addRow(row1);
		model.addRow(row2);
		Vector<Object> vec1=new Vector<>();
		vec1.add(3);
		vec1.add("원숭이");
		vec1.add("상도동");
		model.addRow(vec1);
		
		
	}
	//메인메소드
	public static void main(String[] args) {
		MemberFrame f=new MemberFrame("회원정보 관리");
		f.setBounds(100, 100, 800, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//눌러진 버튼의 action command 를 읽어온다
		String command=e.getActionCommand();
		if(command.equals("add")) {//추가 버튼을 눌렀을때
			addMember();
		}
		
	}
	//회원정보를 추가하는 메소드
	public void addMember() {
		//1.입력한 이름과 주소 읽어서
		String name=text_name.getText();
		String addr=text_addr.getText();

		//2.MemberDto 객체에 담고
		MemberDto dto=new MemberDto();
		dto.setName(name);
		dto.setAddr(addr);
		
		//3.DAO 객체를 이용해서 DB에 저장
		MemberDao dao=new MemberDao();
		boolean isSuccess=dao.insert(dto);
		//4.실제저장확인
		if(isSuccess) {
			JOptionPane.showConfirmDialog(this, name+"의 정보 추가성공");
		}else {
			JOptionPane.showConfirmDialog(this, "정보 추가실패");
		}

	}
}
