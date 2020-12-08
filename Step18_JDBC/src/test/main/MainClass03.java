package test.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainClass03 {
	public static void main(String[] args) {
		String name="톰캣";
		String addr="건물옥상";
		
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
			String sql="INSERT INTO member"
					+" (num, name, addr)"
					+" VALUES(member_seq.NEXTVAL,?,?)";
			pstmt=conn.prepareStatement(sql);
			// ? 에 순서대로 값을 바인딩 하기
			pstmt.setString(1, name);
			pstmt.setString(2, addr);
			//완성된 sql문을 수행하고 변화된 row 의 갯수를 리턴받는다.
			flag=pstmt.executeUpdate();
			System.out.println("회원정보를 저장했습니다");
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
	

