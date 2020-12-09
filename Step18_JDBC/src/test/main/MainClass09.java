package test.main;

import test.member.dao.MemberDao;

public class MainClass09 {
	public static void main(String[] args) {
		//850 번 회원의 정보를 삭제하려고 한다
		
		//이미 만들어진 클래스로 객체를 생성해서
		MemberDao dao=new MemberDao();
		//메소드를 사용하는 사용자 입장이다.
		boolean isSuccess=dao.delete(850);
		
		if(isSuccess) {
			System.out.println("삭제 성공");
		}else {
			System.out.println("삭제 실패");
		}
		
		System.out.println("메인 메소드가 종료됩니다");
	}
}
