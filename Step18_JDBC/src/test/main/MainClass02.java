package test.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * 접속 ip 주소 ㅣ 14.63.164.99
 * 아이디 : acorn01
 * 비밀번호 : tiger01
 * 
 * 위의 DB에 접속해서
 * emp 테이블의 내용중에서 사원번호, 사원이름, 부서번호, 급여
 */
public class MainClass02 {
	public static void main(String[] args) {
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
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="SELECT EMPNO,ENAME,DEPTNO,SAL FROM EMP";
			//sql 문을 대신 실행해주는 PreparedStatement 객체의 참조값 받아오기
			pstmt=conn.prepareStatement(sql);
			//select 문 수행하고 결과 row 를 ResultSet 받아오기
			rs=pstmt.executeQuery();
			while(rs.next()){
				int empno=rs.getInt("EMPNO");
				int deptno=rs.getInt("DEPTNO");
				int sal=rs.getInt("SAL");
				String ename=rs.getString("ENAME");
				System.out.println(empno+"|"+ename+"|"+sal+"|"+deptno);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {}
		}
		System.out.println("메인 메소드가 종료됩니다");
		
	}
}
