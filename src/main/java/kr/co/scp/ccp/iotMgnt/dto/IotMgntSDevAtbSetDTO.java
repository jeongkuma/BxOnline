package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSDevAtbSetDTO {

	@JsonIgnore
	private String dymmy;
	private String svcDevAttbSetSeq;
	private String svcDevAttbSeq;
	private String svcCd;
	private String devAttbCdId;
	private String devAttbSetCdId;
	private String devAttbSetCdNm;
	private String statusCd;
	private String devAttbSetVal;
	private String devClsCd;
	private String regUsrId;
	public String setParentCdId;


}
