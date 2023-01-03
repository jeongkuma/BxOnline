package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSDevMDTO {
	@JsonIgnore
	private String dymmy;
	private String svcDevSeq;
	private String svcCd;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String statusCd;
	private String parentDevSeq;
	private String vendorNm;
	private String normalIconFile;
	private String abnormalIconFile;
	private String abnormalIconFile2;
	private String devMdlVal;
	private String devSeq;
	private String regUsrId;

}
