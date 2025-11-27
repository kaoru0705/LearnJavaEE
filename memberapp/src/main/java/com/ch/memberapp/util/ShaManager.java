package com.ch.memberapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* 이 클래스는 평문의 비밀번호를 암호화 시켜 해시로 결과를 반환하는 역할을 함
 * Java의 암호화 처리는 javaEE, javaME 상관없이 javaSE에서 지원..
 **/
public class ShaManager {
	
	// 메서드 호출 시 매개변수로 평문을 넘겨주면, 암호화 알고리즘을 사용하여 그 값을 반환하는 메서드
	public static String getHash(String password) {
		//String password = "minzino";
		
		StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// 이 비밀번호 평문을 잘게 쪼개자
			//password.getBytes("utf-8");
			
			// 아래의 메서드를 수행하면, 아직 암호화되지 않은 상태의 바이트 배열로 존재하는 데이터를 암호화시킴
			// 32 바이트의 문자열을 반환...
			byte[] hash = digest.digest(password.getBytes("utf-8"));		// 매개변수로 바이트 배열을 원함
			
			// 최종적으로 암호화 결과를 모아놓을 객체
			
			for(int i = 0; i < hash.length; i++) {
				// 아래의 hash[i] 에 혹여나, 1로 시작하는 이진수가 있다면 음수로 해석을하므로, 예상치 못한 암호화 문자열이 반환
				// 따라서 byte[i] 번째의 데이터를 양수로 전환하려면 앞에 int 형의 32비트와의 and 연산을 수행함
				// 예) [0000 0000][0000 0000][0000 0000][1000 0000]
				String hex = Integer.toHexString(0xff & hash[i]);	// 음수가 섞여 있으면 양수가 된다. 
				
				// 한 자리수만 포함되면 64자 미만이 되기 때문에 언제나 64자를 유지하기 위해서는
				// 한 자리수가 나온 문자열을 2자리수로 바꾸자
				if(hex.length() == 1) {		// 2자리가 아닌 1자리수 문자열일 경우 ... 0으로 메꿀 예정
					hexString.append("0");
				}
				hexString.append(hex);
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
}
