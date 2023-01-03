package kr.co.abacus.common.util;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static String getRuleFileKeyName(URI uri) {
		String keyName = "";
		String temp = uri.toString().substring(uri.toString().lastIndexOf("/"));
		String replace1 = uri.toString().replaceFirst(temp, "." + temp.substring(1));
		keyName = replace1.substring(replace1.lastIndexOf("/") + 1);
		return keyName;
	}

	public static String getGUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getRandomStr(int nSize) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < nSize; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) ((rnd.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) ((rnd.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					temp.append((rnd.nextInt(10)));
					break;
			}
		}
		return temp.toString();
	}

	public static String ifNullToEmpty(String obj) {
		String result = "";
		if (obj != null) {
			return obj;
		}
		return result;
	}

	public static String objectIfNullToEmpty(Object obj) {
		String result = "";
		if (obj != null) {
			result = String.valueOf(obj);
			if ("null".equals(result)) {
				result = "";
			}
			return result;
		}
		return result;
	}
	
	/**
	 * <PRE>
	 * @Desc : String / List / Map / [] 형태의 Object들의 null 그리고 빈값을 체크해줍니다.
	 * @Method : isEmptyChk
	 * @param : Object
	 * @return : boolean
	 * @author : kjin
	 * </PRE>
	 */
	public static boolean isEmptyChk(Object s)
	{
		if (s == null) { 
			return true; 
		}
		
		if ((s instanceof String) && (((String)s).trim().length() == 0)) {
			return true; 
		}
		
		if (s instanceof Map) {
			return ((Map<?, ?>)s).isEmpty(); 
		}
		
		if (s instanceof List) {
			return ((List<?>)s).isEmpty(); 
		}
		
		if (s instanceof Object[]) {
			return (((Object[])s).length == 0);
		}
		
		return false; 
	}
	
	/**
	 * 널인 문자열을 스페이스("")로 치환한다 <BR>
	 * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
	 * 
	 * @param s
	 * @return
	 */
	public static String nullToStr(String s)
	{
		if (isEmptyChk(s))
			return "";
		else
			return s.trim();
	}
	
	/**
	 * 널이거나 빈 문자열을 원하는 스트링으로 변환한다 <BR>
	 * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
	 * 
	 * @param org
	 * @param converted
	 * @return
	 */
	public static String nullToStr(String org, String converted)
	{
		if (isEmptyChk(org))
			return converted;
		else
			return org.trim();
	}
	
	/**
	 * 널이거나 빈 문자열을 원하는 스트링으로 변환한다 
	 * 
	 * @param org
	 * @param converted
	 * @return
	 */
	public static String nullToStr(Object org, String converted)
	{
		if (isEmptyChk(org))
			return converted;
		else
			return org.toString();
	}
	
	/**
	 * 널이거나 빈 문자열을 원하는 숫자로 변환한다
	 * 
	 * @param org
	 * @param converted
	 * @return
	 */
	public static int nullToStr(String org, int converted)
	{
		if (isEmptyChk(org))
			return converted;
		else
			return Integer.parseInt(org);
	}
	
	/**
	 * 널이거나 빈 문자열을 원하는 숫자로 변환한다
	 * 
	 * @param org
	 * @param converted
	 * @return
	 */
	public static int nullToStr(Object org, int converted)
	{
		if (isEmptyChk(org))
			return converted;
		else
			return Integer.parseInt(org.toString());
	}
	
}
