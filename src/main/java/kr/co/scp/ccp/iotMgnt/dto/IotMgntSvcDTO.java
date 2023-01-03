package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSvcDTO {

	@JsonIgnore
	private String dymmy;
	private String svcSeq;
	private String svcCd;
	private String svcCdNm;
	private String devClsCd;
	private String svcLevel;
	private String svcOrder;
	private String upSvcSeq;
	private String regUsrId;

}
