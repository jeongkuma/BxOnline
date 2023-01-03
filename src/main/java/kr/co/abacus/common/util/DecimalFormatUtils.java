package kr.co.abacus.common.util;

import java.text.DecimalFormat;

public class DecimalFormatUtils {

	/**
	 * 소수점이 0일 때 지우는 메소드W
	 */
	public static String decimalFormat(String formatter, double number) {
		// 소수점이 0일 때 지우는 메소드W
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(number);

	}
	
	
	public static String  convertToKByte(String formatter, double fileSize) {
		String convertData = "";
		
		if( 1000<=fileSize && fileSize<1000000 ) {
			convertData = decimalFormat(formatter, fileSize/1000) + "KB";
	
		}else if(1000000<=fileSize && fileSize<1000000000) {
			convertData = decimalFormat(formatter, fileSize/1000000) + "MB";
			
		} else if(1000000000<=fileSize) {
			convertData = decimalFormat(formatter, fileSize/1000000000) + "GB";
		} else {
			convertData = fileSize + "";
		}
		
		return convertData;
	}

}
