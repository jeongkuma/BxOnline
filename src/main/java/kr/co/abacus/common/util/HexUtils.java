package kr.co.abacus.common.util;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HexUtils {

	public static int HEX2INT(String hex) {
		return Integer.parseInt(hex, 16);
	}

	public static String convertValue(String type, String hex) {
		String result = null;
		switch (type) {
		case "DEC":
			result = "" + HEX2INT(hex);
			break;
		case "BIN":
			result = HEX2BIN(hex);
			break;
		default:
			// HEX, BCD
			result = hex;
			break;
		}

		return result;
	}

	public static String HEX2BIN(String hex) {
		String result = new BigInteger(hex, 16).toString(2);
		Integer length = result.length();
		if (length < 8) {
			for (int i = 0; i < 8 - length; i++) {
				result = "0" + result;
			}
		}
		return result;
	}

	public static String HEX2BIN(String hex, int bitSize) {
		return lpad(HEX2BIN(hex), "0", bitSize);
	}
	
	public static String BIN2HEX(byte[] byteArray) {
		return Hex.encodeHexString(byteArray);
	}

	public static String BIN2HEX(String bin) {
		String hexString = new BigInteger(bin, 2).toString(16);
		int hexSize = bin.length()/4;
		hexString = lpad(hexString, "0", hexSize);
		return hexString;
	}
	
	
	public static byte[] slice(byte[] in, int start, int end) {
		byte[] slice = Arrays.copyOfRange(in, start, end);
		return slice;
	}

	public static int BIN2INT(String bin) {
		return Integer.parseInt(bin, 2);
	}

	public static byte checkSum(byte[] byteArray, boolean isXor) {
		int i;
		byte checksum = 0;

		if (isXor) {
			for (i = 0; i < byteArray.length; i++) {
				checksum ^= byteArray[i];
			}
		} else {
			for (i = 0; i < byteArray.length; i++) {
				checksum += byteArray[i];
			}
		}

		return checksum;
	}

	public static <K, V> Map<K, V> cloneMap(Map<K, V> original) {
		Map<K, V> copy = new HashMap<>();
		for (K key : original.keySet()) {
			V value = original.get(key);
			if (value instanceof Map) {
				copy.put(key, (V) cloneMap((Map<K, V>) value));
			} else {
				copy.put(key, value);
			}
		}
		return copy;
	}

	public static byte[] HEX2BYTEARRAY(String hexString) {
		if (hexString.length() % 2 == 1) {
			throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
		}

		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			bytes[i / 2] = HEX2BYTE(hexString.substring(i, i + 2));
		}
		return bytes;
	}

	
	public static byte HEX2BYTE(String hexString) {
	    int firstDigit = toDigit(hexString.charAt(0));
	    int secondDigit = toDigit(hexString.charAt(1));
	    return (byte) ((firstDigit << 4) + secondDigit);
	}
	 
	private static int toDigit(char hexChar) {
	    int digit = Character.digit(hexChar, 16);
	    if(digit == -1) {
	        throw new IllegalArgumentException(
	          "Invalid Hexadecimal Character: "+ hexChar);
	    }
	    return digit;
	}

	public static String DEC2BIN(String decValue, int size) {
		int i = Integer.parseInt(decValue);
		String bin = Integer.toBinaryString(i);
		return lpad(bin, "0", size);
	}
	public static String rpad(String str, String pad, int length) {
		str = str == null ? "" : str;
		for (int i = str.length(); i < length; i++) {
			str += pad;
		}
		return str;
	}

	public static String lpad(String str, String pad, int length) {
		str = str == null ? "" : str;
		for (int i = str.length(); i < length; i++) {
			str = pad + str;
		}
		return str;
	}
	
	

}
