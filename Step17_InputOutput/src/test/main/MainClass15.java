package test.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainClass15 {
	public static void main(String[] args) {
		/* FileInputStream 이용해서
		 * c:/myFolder/1.jpg에서 byte 타입 데이터를 읽어들인 다음
		 * 
		 * FileOutputStream 이용해서
		 * c:/myFolder/copied.jpg 파일에 출력하기
		 */
		//파일로 부터 byte 알갱이들을 읽어올수있는 객체
		FileInputStream fis=null;
		//파일로 부터 byte 알갱이들을 출력할수 있는 객체		
		FileOutputStream fos=null;
				
		try {
			//파일로 부터 byte 알갱이들을 읽어올수있는 객체
			fis=new FileInputStream(new File("c:/myFolder/1.jpg"));
			//파일로 부터 byte 알갱이들을 출력할수 있는 객체	
			fos=new FileOutputStream(new File("c:/myFolder/copied.jpg"));
			while(true) {
				int data=fis.read();//1byte 읽어들이기
				System.out.println(data);
				if(data == -1) {//더이상 읽을 데이터가 없다면
					break;//반복문 탈출
				}	
				fos.write(data);//출력준비
				fos.flush();//방출
			}
			System.out.println("파일을 성공적으로 복사했습니다.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {//예외가 발생하던 안하던 보장되는 블럭
			//마무리 작업 (새로 open 한 스트림은 닫아주어야한다)
			try {
				fis.close();
				fos.close();
			}catch(Exception e) {}
			
		}
	}
}
