package kr.co.abacus.common.encryptor;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword("abacus"); // 2번 설정의 암호화 키를 입력

		String url = pbeEnc.encrypt("jdbc:h2:file:./h2/hhidb2"); // 암호화 할 내용
		System.out.println("url = " + url); // 암호화 한 내용을 출력

		String username = pbeEnc.encrypt("hhiadmin"); // 암호화 할 내용
		System.out.println("username = " + username); // 암호화 한 내용을 출력

		String password = pbeEnc.encrypt("hhi0237"); // 암호화 할 내용
		System.out.println("password = " + password); // 암호화 한 내용을 출력

		// 테스트용 복호화
		String des = pbeEnc.decrypt(url);
		System.out.println("des = " + des);
//		String des2 = pbeEnc.decrypt("qsMZcX3awr8F/8mR4gSOM4HRpK5cAPR0r73YATmOiPeId77adM9h15aQAvOiX0Fb");
//		System.out.println("des2 = " + des2);
		String desusername = pbeEnc.decrypt(username);
		System.out.println("des = " + desusername);
		String despassword = pbeEnc.decrypt(password);
		System.out.println("des = " + despassword);
		
//		jdbc-url: jdbc:sqlserver://localhost;databaseName=hhi 
//	        username: SA  
//	        password: Honghyosang1234
		

		url = pbeEnc.encrypt("jdbc:sqlserver://localhost;databaseName=hhi"); // 암호화 할 내용
		System.out.println("url = " + url); // 암호화 한 내용을 출력

		username = pbeEnc.encrypt("SA"); // 암호화 할 내용
		System.out.println("username = " + username); // 암호화 한 내용을 출력

		password = pbeEnc.encrypt("Honghyosang1234"); // 암호화 할 내용
		System.out.println("password = " + password); // 암호화 한 내용을 출력

		// 테스트용 복호화
		des = pbeEnc.decrypt(url);
		System.out.println("des = " + des);
		desusername = pbeEnc.decrypt(username);
		System.out.println("des = " + desusername);
		despassword = pbeEnc.decrypt(password);
		System.out.println("des = " + despassword);
		
	}

}
