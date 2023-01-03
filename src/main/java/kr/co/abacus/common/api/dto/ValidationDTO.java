package kr.co.abacus.common.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ValidationDTO extends CommonDTO {
	private java.math.BigInteger validationSeq;
	private String httpUri;			// URI
	private String httpType;		// HEADER KEY
	private String dataKey;			// DATA KET
	private String dataType;		// DATA TYPE
	private String requiredKey; 	// DEFAULT YN
	private String notNull;			// ISNULL
	private long length;			// data size
	private long minLength;		// data min
	private long maxLength;		// data max
	private String accept;			// 허용 정규식
	private String reject;			// 허용안되는 정규식
	private String noWhitespace;	// 공백 허용여부
	private String minValue;		// data min
	private String maxValue;		// data max
	private String uuid;
}
