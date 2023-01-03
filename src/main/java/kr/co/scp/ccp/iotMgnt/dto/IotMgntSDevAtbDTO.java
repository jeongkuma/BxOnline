package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSDevAtbDTO {

	@JsonIgnore
	private String dymmy;
	private String svcDevAttbSeq;
	private String svcDevSeq;
	private String svcCd;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devAttbCdId;
	private String devAttbCdNm;
	private String inputType;
	private String devAttbType;
	private String statusCd;
	private String paramKey;
	private String colNmCd;
	private String conNmCd;
	private String staAvgNmCd;
	private String staSumNmCd;
	private String detNmCd;
	private String mapYn;
	private String unit;
	private String orderNo;
	private String devSeq;
	private String regUsrId;

}
