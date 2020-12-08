package test.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/*
 * member 테이블에서
 * 
 * num 이 804인 회원의
 * 
 * addr을 노량진으로 수정하는 코드를 작성해보시오
 */
public class MainClass04 {
	public static void main(String[] args) {
		int num=804;
		String addr="노량진";
		//DB연결 객체를 담을 지역변수
		Connection conn=null;
		
		try {
			//드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//접속할 DB정보
			String url="jdbc:oracle:thin:@14.63.164.99:1521:xe";
			
			//접속하고 Connection 객체의 참조값 읽어오기
			conn=DriverManager.getConnection(url,"acorn01","tiger01");
			
			System.out.println("Oracle DB접속 성공");
		}catch(Exception e){
				e.printStackTrace();
		}
		
	//sql문 대신 실행해주는 객체의 참조값을 담는 지역변수
			PreparedStatement pstmt=null;
			int flag=0;
			try {
				String sql="UPDATE member"
						+" SET addr=?"
						+" WHERE num=?";
				pstmt=conn.prepareStatement(sql);
				// ? 에 순서대로 값을 바인딩 하기
				pstmt.setString(1, addr);
				pstmt.setInt(2, num);
				//완성된 sql문을 수행하고 변화된 row 의 갯수를 리턴받는다.
				flag=pstmt.executeUpdate();
				System.out.println("회원정보를 수정했습니다");
			}catch(Exception e) {
					e.printStackTrace();
			}finally {
				try {
					if(pstmt!=null)pstmt.close();
					if(conn!=null)pstmt.close();
				}catch(Exception e) {}
			}
		}
	}


