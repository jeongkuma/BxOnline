package kr.co.scp.ccp.iotSelDevice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotEDevCurValDTO {
	@JsonIgnore
	private String dymmy;
	//private String entrDevAttbSeq;
	private String eDevCurValSeq;
	private String entrDevSeq;
	private String eDevDetSetSeq;
	private String detStatusCdId;
	private String detStatusCdNm;
	private String devAttbCdNm;
	private String devVal;
	private String mapYn;
	private String unit;
	private String orderNo;

//	private String devAttbCd;
//	private String devMdlCd;
//	private String devMdlNm;
//	private String inputType;
//	private String pamKey;
//	private String detNm;

}
