package json;

import org.json.JSONObject;

/*
 * [ Java 객체와 JSON 문자열의 관계 ]
 * 
 * Map => JSONObject => { }
 * 
 * List => JSONArray => [ ]
 * 
 * { } => JSONObject
 * [ ] => JSONArray
 * 10 => int
 * 10.1 => double
 * "xxx" => String
 * true => boolean
 */
public class MainClass06 {
	public static void main(String[] args) {
		String info="{\"num\":1,\"name\":\"김구라\",\"isMan\":true}";
		System.out.println(info);
		// {} 를 JSONObject 로 바꿔준다
		JSONObject obj=new JSONObject(info);
		// "num" 이라는 키값으로 int 1 을 빼내기
		obj.getInt("num");
		// "name"이라는 키값으로 String "김구라"를 빼내기
		obj.getString("name");
		// "isMan" 이라는 키값으로 boolean true 빼내기
		obj.getBoolean("isMan");
		
	}
}
