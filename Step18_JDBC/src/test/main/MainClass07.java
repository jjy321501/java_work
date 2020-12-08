package test.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import test.member.dto.MemberDto;
import test.util.DBConnect;

/*
 * Scanner 객체를 이용해서 검색할 회원의 번호를 입력 받아서
 * 입력받은 숫자를 이용해서 select 문을 수행하고
 * 결과값을 MemberDto 객체를 생성해서 담아보세요
 * 
 * 결과가 없다면 MemberDto 객체를 생성하지 마세요
 */
public class MainClass07 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("검색할 번호 입력 :");
		int num=sc.nextInt();
		
		//검색된 회원정보가 담길 MemberDto 객체의 참조값을 담을 지역변수
		MemberDto dto=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			//Connection 객체의 참조값 얻어오기
			conn=new DBConnect().getConn();
			//실행할 sql 문의 뼈대 준비하기
			String sql="SELECT name,addr FROM member WHERE num=?";
			//preparedStatement 객체 참조값 얻어오기
			pstmt=conn.prepareStatement(sql);
			//?에 값 바인딩해서 select 문 완성하기
			pstmt.setInt(1, num);
			//쿼리문 (SELECT) 수행하고 결과를 ResultSet 으로 받아오기
			rs=pstmt.executeQuery();
			
			if(rs.next()) {//select 된 결과가 있다면
				dto=new MemberDto();
				dto.setNum(num);
				dto.setName(rs.getString("name"));
				dto.setAddr(rs.getString("addr"));
			}else {
				System.out.println("해당 번호가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)pstmt.close();
				if(conn!=null)pstmt.close();
				if(pstmt!=null)pstmt.close();
			}catch(Exception e) {}
		}
		//위의 try~catch~finally 절이 수행한뒤에 select 된 결과가 있는지 없는지를
		//여기에서 쉽게 판별할 수 있는 방법이 있나요
		if(dto==null) {
			System.out.println(num+"번 회원은 존재하지 않습니다");
		}else {
			System.out.println("번호:"+dto.getNum()+
					", 이름:"+dto.getName()+", 주소:"+dto.getAddr());
		}
	}
}
