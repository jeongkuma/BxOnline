package kr.co.abacus.common.util;

import java.util.Random;

public class TemporyPassword {
	
	public static String randomPassword(int size) {
		StringBuffer buffer = new StringBuffer();
	    Random random = new Random();
			
	    String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");
			
	   for (int i = 0; i < size; i++) {
	       buffer.append(chars[random.nextInt(chars.length)]);
	   }
	   return buffer.toString();

	}
	
	
	public static String randomValue(String type, int cnt) {
		
	    StringBuffer strPwd = new StringBuffer();
	    char str[] = new char[1];
	    // 특수기호 포함
	    if (type.equals("P")) {
	        for (int i = 0; i < cnt; i++) {
	            str[0] = (char) ((Math.random() * 94) + 33);
	            strPwd.append(str);
	        }
	    // 대문자로만
	    } else if (type.equals("A")) {
	        for (int i = 0; i < cnt; i++) {
	            str[0] = (char) ((Math.random() * 26) + 65);
	            strPwd.append(str);
	        }
	    // 소문자로만
	    } else if (type.equals("S")) {
	        for (int i = 0; i < cnt; i++) {
	            str[0] = (char) ((Math.random() * 26) + 97);
	            strPwd.append(str);
	        }
	    // 숫자형으로
	    } else if (type.equals("I")) {
	        int strs[] = new int[1];
	        for (int i = 0; i < cnt; i++) {
	            strs[0] = (int) (Math.random() * 9);
	            strPwd.append(strs[0]);
	        }
	    // 소문자, 숫자형
	    } else if (type.equals("C")) {
	        Random rnd = new Random();
	        for (int i = 0; i < cnt; i++) {
	            if (rnd.nextBoolean()) {
	                strPwd.append((char) ((int) (rnd.nextInt(26)) + 97));
	            } else {
	                strPwd.append((rnd.nextInt(10)));
	            }
	        }
	    }
	    return strPwd.toString();
	}  
}

