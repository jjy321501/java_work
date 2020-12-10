package test.dept.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dept.dto.DeptDto;
import test.member.dto.MemberDto;
import test.util.DBConnect;

/*
 *  회원정보(member 테이블) 의 내용을
 *  select
 *  insert
 *  update
 *  delete 
 *  작업을 할 객체를 생성할 클래스 설계하기 
 *  
 *  - 필요한 모든 객체를 담을 지역변수를 선언하는 위치도 중요하다.
 *  - 필요한 객체를 생성하는 위치도 중요하다
 *  
 *  Data Access Object (DAO)
 *  
 *  -DB에 INSERT ,UPDATE ,DELETE ,SELECT 작업을 수행하는 객체
 *  -Table 마다 하나의 DAO 혹은 주제(카테고리) 마다 하나의 DAO 를 작성하게된다.
 *  -DAO를 만들기 위해서는 DTO 클래스를 미리 설계하고 만들어야한다
 *  -주제 (카테고리) 에 관련된 DAO 는 여러개의 Table 의 Join 이 일어날수도 있다.
 *  -따라서 하나의 Table당 하나의DAO 는 아닌것이다
 *  예를 들어 사원의 정보를 출력한다고 가정하면
 *  emp, dept, salgrade 3개의 테이블의 join 이 일어날 수도 있다.
 *  
 */
public class DeptDao {
	//모든 회원의 정보를 Select 해서 리턴하는 메소드
	public List<DeptDto> selectAll() {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<DeptDto> li=new ArrayList<>();
		try {
			conn=new DBConnect().getConn();
			String sql="SELECT deptno,dname,loc"
					+ " FROM dept"
					+ " ORDER BY deptno DESC";
			pstmt=conn.prepareStatement(sql);
			// ? 에 바인딩 할게 있으면한다
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				DeptDto dto=new DeptDto();
				dto.setDeptno(rs.getInt("deptno"));
				dto.setDname(rs.getString("dname"));
				dto.setLoc(rs.getString("loc"));
				
				//새로 생성한 MemberDto 객체의 참조값을 ArrayList 객체에 누적시킨다	
				li.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {}
		}
		return li;
	}
	
	//회원 한명의 정보를 Select 해서 리턴하는 메소드
	public DeptDto select(int deptno) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		DeptDto dto=null;
		try {
			conn=new DBConnect().getConn();
			String sql="SELECT dname,loc FROM dept"
					+ " WHERE deptno=?";
			pstmt=conn.prepareStatement(sql);
			// ? 에 값 바인딩하기
			pstmt.setInt(1, deptno);
			rs=pstmt.executeQuery();
			if(rs.next()) {//select 된 row가 있다면 
				//결과를 MemberDto 객체를 생성해서 담는다
				dto=new DeptDto();
				dto.setDeptno(deptno);
				dto.setDname(rs.getString("dname"));
				dto.setLoc(rs.getString("loc"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {}
		}
		return dto;
	}
	
	
	
	
	//1. 회원 한명의 정보를 저장하는 메소드를 만들어 보세요.
	// 메소드명 : insert
	// 리턴 type : 알아서
	// 메소드에 전달하는 인자의 type : MemberDto 
	public boolean insert(DeptDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int flag=0;
		try {
			conn=new DBConnect().getConn();
			String sql="INSERT INTO dept"
					+ " (deptno,dname,loc)"
					+ " VALUES(dept_seq2.NEXTVAL, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getDname());
			pstmt.setString(2, dto.getLoc());
			//INSERT 문 수행하기  ( 1개의 row 가 추가 되었으므로 1 이 러턴된다)
			flag=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			}catch(Exception e) {}
		}
		if(flag>0) {
			return true;
		}else {
			return false;
		}

	}
	//2. 회원 한명의 정보룰 수정하는 메소드를 만들어 보세요.
	// 메소드명 : update
	// 리턴 type : 알아서
	// 메소드에 전달하는 인자의 type : MemberDto 
	public boolean update(DeptDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int flag=0;
		try {
			conn=new DBConnect().getConn();
			//실행할 sql 문 작성
			String sql="UPDATE dept"
					+ " SET dname=?, loc=?"
					+ " WHERE deptno=?";
			pstmt=conn.prepareStatement(sql);
			//?에 값을 바인딩 할게 있으면 여기서 한다.
			pstmt.setString(1, dto.getDname());
			pstmt.setString(2, dto.getLoc());
			pstmt.setInt(3, dto.getDeptno());
			flag=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {}
		}
		if(flag>0) {
			return true;
		}else {
			return false;
		}


	}


	//회원 한명의 정보를 삭제하는 메소드
	public boolean delete(int deptno) {
		
		//필요한 참조값을 담을 지역 변수 미리 만들고 초기화 하기 
		Connection conn=null;
		PreparedStatement pstmt=null;
		int flag=0;
		try {
			//Connection 객체의 참조값 얻어오기
			conn=new DBConnect().getConn();
			//실행할 sql 문의 뼈대 준비하기
			String sql="DELETE FROM dept WHERE deptno=?";
			//PreparedStatement 객체의 참조값 얻어오기
			pstmt=conn.prepareStatement(sql);
			//? 에 값 바인딩하기
			pstmt.setInt(1, deptno);
			//sql 문 실행하고 변화된 row  의 갯수 리턴 받기
			flag=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//마무리 작업
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {}
		}
		if(flag>0) {
			return true;
		}else {
			return false;
		}	
	}
}
