package kr.co.abacus.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.logger.Log;
import kr.co.abacus.common.rule.annotation.Rule;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Rule
public class ConversionUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	public static Map<String, Object> multiSubstring(String str, String key, String... args) {
		Map<String, Object> result = null;
		int beginIndex = 0;
		int endIndex = 0;

		if (args != null) {
			result = new java.util.LinkedHashMap<String, Object>();
			for (int i = 1; i <= args.length; i++) {
				endIndex = beginIndex + Integer.parseInt(args[i - 1]);
				(result).put(key + i, str.substring(beginIndex, endIndex));
				beginIndex = endIndex;
			}
		}

		return result;
	}
	
	// tcpCollect of parse 
//	public static int bitSubstring(String str, String key, String... args) {
//		String strValue = "";
//		
//		byte[] strByte = hexStringToByteArray(str);
//
//		if (args != null) {
//			byte[] temp = collectData(strByte, Integer.parseInt(args[0]), Integer.parseInt(args[0]) + 1);	
//			
//			for(int i=0;i<temp.length;i++) {
//				strValue += String.valueOf(bitCheck(temp[i], Integer.parseInt(args[1]), Integer.parseInt(args[2])));
//			}
//		}
//
//		return strValue;
//	}
	
	public static String byPassValue(String value) {
		return value;
	}
	
	
	public static String byNoValue(String value) {
//		Log.debug("=== byNoValue :: " + value );
		return "";
	}
	
	public static String byNoValue(String value, String fByte) {
//		Log.debug("=== byNoValue 2 :: value :: " + value );
//		Log.debug("=== byNoValue 2 :: fByte :: " + fByte );
		return fByte;
	}
	
	public static String byNoValue(String value, String fByte, String lByte, String tmp) {
//		Log.debug("=== byNoValue 4 :: value :: " + value );
//		Log.debug("=== byNoValue 4 :: fByte :: " + fByte );
//		Log.debug("=== byNoValue 4 :: lByte :: " + lByte );
//		Log.debug("=== byNoValue 4 :: tmp :: " + tmp );
		return fByte;
	}
	
	public static String byHexString(String value, String fByte, String lByte) {
//		Log.debug("=== byHexString 2 :: " + value );
//		Log.debug("=== byHexString 2 :: " + fByte + " :: " + lByte );
		String rn = value.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(lByte) * 2);
		Log.debug("=== byHexString 2 :: return :: " + rn );
		return rn;
	}
	
	public static String byHexString(String value, String fByte, String lByte, String tmp) {
//		Log.debug("=== byHexString 2 :: " + value );
//		Log.debug("=== byHexString 2 :: " + fByte + " :: " + lByte );
		String rn = value.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(lByte) * 2);
		Log.debug("=== byHexString :: return :: " + rn );
		return rn;
	}
	
	public static String byHexString(String value, String fByte, String lByte, String tmp, String tmp1) {
//		Log.debug("=== byHexString 4 :: " + value );
//		Log.debug("=== byHexString 4 :: " + fByte + " :: " + lByte );
		String rn = value.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(lByte) * 2);
		Log.debug("=== byHexString :: return :: " + rn );
		return rn;
	}
	
	
	public static Long byHexLong(String value, String fByte, String lByte) {
		return byHexLong( value,  fByte,  lByte, "0");
	}
	
	public static Long byHexLong(String value, String fByte, String lByte, String tmp) {
		String rn = value.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(lByte) * 2);
		// System.out.print(rn + "  ");
		Long lnRn = null;
		if ("-1".equals(tmp) || "0".equals(tmp)){
			lnRn = convertHexToLong(rn, tmp);
		}
		return lnRn;
	}
	
	
	public static Long byHexLong(String value, String fByte, String lByte, String tmp, String tmp1) {
		return byHexLong( value,  fByte,  lByte, tmp1);
	}
	
	public static String hexToAscii(String value, String fByte, String lByte) {
		String hexStr = value.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(lByte) * 2);
		// System.out.print(hexStr + "  ");
		String rn = hexToAscii(hexStr);
		Log.debug("=== hexToAscii :: return :: " + rn );
		return rn;
	}
	
	public static String hexToAscii(String value, String fByte, String lByte, String tmp) {
		return hexToAscii(value, fByte, lByte) ;
	}
	
	
	public static String hexToAscii(String value, String fByte, String lByte, String tmp, String tmp1) {
		return hexToAscii(value, fByte, lByte) ;
	}
	
	public static String hexToAscii(String hexStr) {
	    StringBuilder output = new StringBuilder("");
	     
	    for (int i = 0; i < hexStr.length(); i += 2) {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	     
	    return output.toString();
	}
	
	// tcpCollect of parse 
	// BCD  
	public static String byteSubstringByBCD(String str, String fByte, String lByte, String point, String signed) {
		
		String strValue = "";
		int check = 0;
		
		
		try {
			byte[] strByte = hexStringToByteArray(str);
			
	
			if (fByte != null && lByte !=null && signed != null) {
				byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte));
				
				for(int i=0;i<temp.length;i++) {
					if (i==0) {
						if (bitCheck(temp[0], 0, 4) == 15) {
							check = -1;
							strValue += String.valueOf(bitCheck(temp[i], 4, 4));
						} else {
							check = 1;
							strValue += String.valueOf(bitCheck(temp[i], 0, 4)) + String.valueOf(bitCheck(temp[i], 4, 4));
						}
					} else 
						strValue += String.valueOf(bitCheck(temp[i], 0, 4)) + String.valueOf(bitCheck(temp[i], 4, 4));
				}
				
				
				
				if (signed.equals("-1")) {
					if (strValue.equals("01")) {
						strValue = "Y";
					} else {
						strValue = "N";
					}
				} else if (signed.equals("0")){
					if (!point.equals("0")){
						strValue = strValue.substring(0, strValue.length() - Integer.parseInt(point)) + "." + strValue.substring(strValue.length() - Integer.parseInt(point), strValue.length());
						strValue = String.valueOf(Double.parseDouble(strValue) * check);
					} else {						
						strValue = String.valueOf(Long.parseLong(strValue) * check);
					}
				} 
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		Log.debug("=== byteSubstringByBCD :: str => " + str + "\n:: fByte => " + fByte + ":: lByte =>" + lByte +  ":: point =>" + point+  ":: signed =>" + signed + ":: strValue=>" + strValue);
		
		return strValue;
	}
	
	public static String byteYYMMDDByBCD(String str, String fByte, String lByte, String point, String signed) {
		String strValue = "";
		int check = 0;
		
		byte[] strByte = hexStringToByteArray(str);

		if (fByte != null && lByte !=null && signed != null) {
			byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte));
			
			for(int i=0;i<temp.length;i++) {
				strValue += String.valueOf(bitCheck(temp[i], 0, 4)) + String.valueOf(bitCheck(temp[i], 4, 4));
			}
			
		}
		
		Log.debug("=== byteYYMMDDByBCD :: str => " + str + "\n:: fByte => " + fByte + ":: lByte =>" + lByte +  ":: point =>" + point+  ":: signed =>" + signed + ":: strValue=>" + strValue);
		
		return strValue;
	}
	
	public static String byteYYMMDDByHex(String str, String fByte, String lByte, String point, String signed) {
		String strValue = "";
		String strTemp = "";
		int check = 0;
		
		byte[] strByte = hexStringToByteArray(str);

		if (fByte != null && lByte !=null && signed != null) {
			byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte));
			
			for(int i=0;i<temp.length;i++) {
				strTemp = String.valueOf(bitCheck(temp[i], 0, 4) * 16 + bitCheck(temp[i], 4, 4));
				if (strTemp.length() < 2)
					strTemp = "0" + strTemp;
				strValue += strTemp;
			}
		}
		
		Log.debug("=== byteYYMMDDByHex :: str => " + str + "\n:: fByte => " + fByte + ":: lByte =>" + lByte +  ":: point =>" + point+  ":: signed =>" + signed + ":: strValue=>" + strValue);
		
		return strValue;
	}
	
	private static String removeZero(String str) {
		if (str.indexOf("0") == 0 && str.indexOf(".") != 1 && str.length() > 1) {
			str = removeZero(str.substring(1, str.length()));
		}
		return str;
	}
	
	// tcpCollect of parse 
	// ByteValue
	public static double byteSubstringWithByteValue(String str, String fByte, String lByte, String point, String signed) {
		double lValue = 0;
		
		String unsignedCheck = str.substring(Integer.parseInt(fByte) * 2, Integer.parseInt(fByte) * 2 + 1);
		byte[] strByte = hexStringToByteArray(str);

		if (fByte != null && lByte != null) {
			byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte));
			
			for(int i=0;i<temp.length;i++) {
				if (unsignedCheck.equals("F") || unsignedCheck.equals("f")) {
					temp[i] = (byte) (temp[i] ^ (byte)0xFF);
				}
				
				lValue += bitCheck(temp[i], 0, 4) * (int)Math.pow(16, temp.length*2 - ((i+1)*2 - 1)) 
					+ bitCheck(temp[i], 4, 4) * (int)Math.pow(16, temp.length*2 - (i+1)*2);
			}
			
			if (unsignedCheck.equals("F") || unsignedCheck.equals("f")) {
				lValue += 1;
			}
		}
		
		lValue = lValue / Math.pow(10, Integer.parseInt(point));
		
		if (unsignedCheck.equals("F") || unsignedCheck.equals("f")) {
			lValue = -1 * lValue;
		}

		Log.debug("=== byteSubstringWithByteValue :: str => " + str + "\n:: fByte => " + fByte + ":: lByte =>" + lByte +  ":: point =>" + point+  ":: signed =>" + signed + ":: lValue=>" + lValue);
		
		return lValue;
	}
	
	// tcpCollect of parse 
	// ByteValue
	public static int byteSubstringWithBitValue(String str, String fByte, String lByte, String fBit, String lBit, String point, String signed) {
		int iValue = 0;
		int left = 0;
		int right = 0;
		
		byte[] strByte = hexStringToByteArray(str);

		if (fByte != null && lByte !=null && fBit != null && lBit != null) {
			byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte));
			
//			if (Integer.parseInt(fBit) == 0) {
//				left = 0;
//				right = 8-Integer.parseInt(lBit);
//			} else if (Integer.parseInt(lBit) == 8) {
//				left = Integer.parseInt(fBit);
//				right = Integer.parseInt(fBit);
//			} else {
//				left = Integer.parseInt(fBit);
//				right = Integer.parseInt(fBit) + (8 - Integer.parseInt(lBit));
//			}
			
			if (Integer.parseInt(fBit) == 0) {
				left = 8 - Integer.parseInt(lBit);
				right = 8 - Integer.parseInt(lBit);
			} else if (Integer.parseInt(lBit) == 8) {
				left = 0;
				right = Integer.parseInt(fBit);
			} else {
				left = 8-Integer.parseInt(lBit);
				right = Integer.parseInt(fBit) + (8 - Integer.parseInt(lBit));
			}
			
			iValue += bitCheck(temp[0], left, right);
		}
		
		iValue = iValue * (int)Math.pow(10, Integer.parseInt(point));
		
		if (signed == "1") {
			iValue = -1 * iValue;
		}

		Log.debug("=== byteSubstringWithBitValue :: str => " + str + "\n:: fByte => " + fByte + ":: lByte =>" + lByte +  ":: point =>" + point+  ":: signed =>" + signed + ":: iValue=>" + iValue);
		
		return iValue;
	}
	
	public static String collectString(String str, String size) {
		Log.debug("=== collectString :: " + str + " :: size ::  " + size );
		return str.substring(Integer.parseInt(size) * 2, str.length());
	}
	
	public static boolean bccCheck(String str) throws Exception {
		byte[] strByte = hexStringToByteArray(str);
		
		if (!bccCheckByXOR(str))
			throw new BizException("TcpProtocolError");
		
		if(!bccCheckBySum(str))
			throw new BizException("TcpProtocolError");
		
		return true;
	}

	public static List<Map<String, Object>> multiSubstringList(JsonNode jsonNode, String key, String... args) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (JsonNode node : jsonNode) {
			list.add(multiSubstring(node.asText(), key, args));
		}

		return list;
	}

	public static List<Map<String, Object>> multiSubstringList(String jsonString, String key, String... args) throws Exception {
		JsonNode jsonNode = mapper.readValue(jsonString, JsonNode.class);

		return multiSubstringList(jsonNode, key, args);
	}

	public static String substring(String str, String start) {
		return StringUtils.substring(str, Integer.parseInt(start));
	}

	public static String substring(String str, String start, String end) {
		return StringUtils.substring(str, Integer.parseInt(start), Integer.parseInt(end));
	}

	public static String getGUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getRandomStr(String length) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < Integer.parseInt(length); i++) {
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

	public static int convertStringToInt(String str, int radix) {
		return Integer.parseInt(str, radix);
	}

	public static String convertStringToString(String str, int radix) {
		return String.valueOf(Integer.parseInt(str, radix));
	}

	public static int convertHexToInt(String hex) {
		String temp = hex.replaceFirst("0x", "");
		return Integer.parseInt(temp, 16);
	}
	
	public static long convertHexToLong(String hex, String signed) {
		String temp = hex.replaceFirst("0x", "");
		long rn = 0;
		if (signed.equals("-1")) {
			rn = Long.parseUnsignedLong(temp, 16);
		} else if (signed.equals("0")){
			rn = Long.parseLong(temp, 16);
		} 
		return rn;
	}
	
	public static String convertHexToString(String hex) {
		String temp = hex.replaceFirst("0x", "");
		return String.valueOf(Integer.parseInt(temp, 16));
	}

	/**
	 * 문자열 byte 단위로 자르기
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	public static String getMaxByteString(String str, int maxLen) {
		StringBuilder sb = new StringBuilder();
		int curLen = 0;
		String curChar;
		for (int i = 0; i < str.length(); i++) {
			curChar = str.substring(i, i + 1);
			curLen += curChar.getBytes().length;
			if (curLen > maxLen)
				break;
			else
				sb.append(curChar);
		}
		return sb.toString();
	}

	/**
	 * GZIPOutputStream을 이용하여 문자열 압축하기
	 * 
	 * @param str		String
	 * @return			Byte
	 * @throws IOException
	 */
	public static byte[] zipStringToBytes(String str) throws Exception  {
		
		if (str == null || str.length() == 0)
			return null;
		
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(obj);
		gos.write(str.getBytes("UTF-8"));
		gos.close();
		
		return obj.toByteArray();
	}
	
	/**
	 * GZIPOutputStream을 이용하여 문자열 압축하기
	 * 
	 * @param str		String
	 * @return			String (Base64 Encode)
	 * @throws IOException
	 */
	public static String zipStringToString(String str) throws Exception {
		
		if (str == null || str.length() == 0)
			return null;
		
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(obj);
		gos.write(str.getBytes());
		IOUtils.closeQuietly(gos);
		
		byte[] bytes = obj.toByteArray();
		return Base64.encodeBase64String(bytes);
	}
	
	/**
	 * GZIPInputStream을 이용하여 byte배열 압축해제하기
	 * 
	 * @param buf		Byte
	 * @return			String
	 * @throws IOException
	 */
	public static String unzipStringFromBytes(byte[] buf) throws Exception {
		
		if (buf == null )
			return null;
		
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(buf));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		
		String outStr = "";
		String line;
		
		while ((line=bf.readLine())!=null) {
			outStr += line;
		}
		
		return outStr;
	}
	
	/**
	 * GZIPInputStream을 이용하여 압축된 Base64 인코딩 문자열 압축해제하기
	 * 
	 * @param zippedBase64Str	String (Base64 Encode)
	 * @return					String
	 * @throws IOException
	 */
	public static String unzipStringFromString(String zippedBase64Str) throws Exception {
		
		if (zippedBase64Str == null || zippedBase64Str.length() == 0)
			return null;
		
		String result = null;
		byte[] bytes = Base64.decodeBase64(zippedBase64Str);
		GZIPInputStream gis = null;
		
		try {
			gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
			result = IOUtils.toString(gis);
		} finally {
			IOUtils.closeQuietly(gis);
		}
		return result;
	}
	
	public static byte[] collectData(byte[] data, int firstloc, int lastloc) { 
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		 
		output.write(data, firstloc, lastloc - firstloc);
		 
		return output.toByteArray();
	}
	
	public static int bitCheck(byte data, int left, int right) {
		int ivalue = (data & 0xff);
		byte bvalue;
		
		ivalue <<= left;
		bvalue = (byte)ivalue;
		ivalue = (bvalue & 0xff);
		
		ivalue >>>= right;
		bvalue = (byte)ivalue;
		ivalue = (bvalue & 0xff);
			   
		return ivalue; 
	}
	
	public static int bitCheck2(byte data, int left, int right) {
		int value = data;
//		byte result = (byte)((value<<left)>>>right);
		
		byte temp = (byte)(value<<left);
		value = temp;
		value = value >>>= right;
		
			   
		return value;
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static boolean bccCheckByXOR(String str) {
		byte[] strByte = hexStringToByteArray(str);
		byte[] temp = collectData(strByte, 0, strByte.length-2);
		
		byte xor = temp[0]; 
		for (int i=1;i<temp.length;i++) {
			xor = (byte)(xor ^ temp[i]);
		}
		
		byte[] test = new byte[10];
		test[0] = xor;
				
		if (strByte[strByte.length-2] == xor)
			return true;
		else 
			return false;
	}
	
	public static boolean bccCheckBySum(String str) {
		byte[] strByte = hexStringToByteArray(str);
		byte[] temp = collectData(strByte, 0, strByte.length-2);
		
		byte sum = 0;
		for (byte a:temp) {
			sum += a;
		}
		
		byte[] test = new byte[10];
		test[0] = sum;
				
		if (strByte[strByte.length-1] == sum)
			return true;
		else 
			return false;
	}
	
	//양수 변환
	public static double byteSubstringWithByteUnsignedValue(String str, String fByte, String lByte, String point, String signed) { 
        double lValue = 0; 
        byte[] strByte = hexStringToByteArray(str); 

        if (fByte != null && lByte != null) { 
        	byte[] temp = collectData(strByte, Integer.parseInt(fByte), Integer.parseInt(lByte)); 

        	for(int i=0;i<temp.length;i++) { 
        		lValue += bitCheck(temp[i], 0, 4) * (int)Math.pow(16, temp.length*2 - ((i+1)*2 - 1))  
        				+ bitCheck(temp[i], 4, 4) * (int)Math.pow(16, temp.length*2 - (i+1)*2); 
        	}
        } 
        lValue = lValue / Math.pow(10, Integer.parseInt(point));

        return lValue;
	} 

}