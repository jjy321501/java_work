package test.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import test.util.DBConnect;

/*
 * Scanner 객체를 이용해서 검색할 회원의 번호를 입력 받아서
 * DB에서 SELECT 하고 
 * 결과 값이 있으면 해당 회원의 정보를 콘솔창에 출력하고
 * 결과 값이 없으면 해당 회원은 존재하지 않습니다. 라고 출력해보시오
 * hint : String sql="SELECT num,name,addr FROM member WHERE num=?";
 * 
 * 예)
 * 검색할 회원 번호 입력 :
 * 801
 * 801 번 회원은 존재하지 않습니다.
 * 
 * 예)
 * 검색할 회원 번호 입력 :
 * 850
 * 번호 : 850, 이름 : 톰캣, 주소 : 건물 옥상
 */
public class MainClass06 {
	
	public static void main(String[] args) {
		//콘솔창으로부터 입력받을 객체 생
		Scanner sc=new Scanner(System.in);
		System.out.println("검색할 회원 정보 :");
		//콘솔창에 입력한 값을 정수로 얻어낸다
		int num =sc.nextInt();
		//필요한 객체를 담을 지역변수 미리 만들기
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
			/*
			 * member table 에서 num 은 PRIMARY KEY 값이다
			 * 따라서 SELECT 된 결과 row 의 갯수는 
			 * 0 이거나 1이 된다
			 */
			
			if(rs.next()) {
				//select 된 row 가 존재한다면 커서가 위치한곳의 데이터 얻어오기
				String name=rs.getString("name");
				String addr=rs.getString("addr");
				System.out.println("번호 :"+num+"이름 :"+name+" 주소 : "+addr);
			}else {
				System.out.println(num+"번 회원은 존재하지 않습니다");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//마무리 작업
			try {
				if(conn!=null)pstmt.close();
				if(pstmt!=null)pstmt.close();
				if(rs!=null)pstmt.close();
			}catch(Exception e) {}
		}
	
	}
	
}
