package test.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import test.util.DBConnect;

/*
 * memeber 테이블에서
 * 회원번호가 801 번 회원의 정보를 삭제하는 기능을 완성해보자
 * hint
 * new DBConnect().getConn();
 */
public class MainClass05 {
	public static void main(String[] args) {
		//삭제할 회원의 번호
		int num=847;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		int flag=0;
		try {
			//Connection 객체의 참조값 얻어오기
			conn=new DBConnect().getConn();
			//실행할 sql 문의 뼈대 준비하기
			String sql=("Delete FROM member"
						+" where num=?");
			//preparedStatement 객체 참조값 얻어오기
			pstmt=conn.prepareStatement(sql);
			//?에 값 바인딩하기
			flag=pstmt.executeUpdate();
			//sql문 실행하고 변화된 row 갯수 리턴하기
			pstmt.setInt(1, num);
			System.out.println("회원정보를 삭제했습니다");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//마무리 작업
			try {
				if(conn!=null)pstmt.close();
				if(pstmt!=null)pstmt.close();
			}catch(Exception e) {}
		}
		if(flag>0) {
			System.out.println("작업 성공");
		}
		if(flag<0) {
			System.out.println("작업 실패");
		}
	}
}
