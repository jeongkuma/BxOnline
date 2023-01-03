package kr.co.auiot.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCommUtil {
	public static Object stringToObject(String jsonData, Class classString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonData, classString);
	}

	public static Object stringToObject(String jsonData, TypeReference valueTypeRef) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonData, valueTypeRef);
	}
	public static String cleanString(String aString) {
		if (aString == null) {
			return null;
		}
		String cleanString = "";
		for (int i = 0; i < aString.length(); ++i) {
			cleanString += cleanChar(aString.charAt(i));
		}
		return cleanString;
	}

	private static char cleanChar(char aChar) {
		// 0 - 9 
		for (int i = 48; i < 58; ++i) {
			if (aChar == i) {
				return (char) i;
			}
		}
		// 'A' - 'Z' 
		for (int i = 65; i < 91; ++i) {
			if (aChar == i) {
				return (char) i;
			}
		}
		// 'a' - 'z' 
		for (int i = 97; i < 123; ++i) {
			if (aChar == i) {
				return (char) i;
			}
		}
		// other valid characters 
		return getSpecialLetter(aChar);
	}

	public static char getSpecialLetter(char aChar) {
		switch (aChar) {
			case '/':
				return '/';
			case '.':
				return '.';
			case '-':
				return '-';
			case '_':
				return '_';
			case ' ':
				return ' ';
			case ':':
				return ':';
			case '&':
				return '&';
			default:
				return '%';
		}
	}
}
