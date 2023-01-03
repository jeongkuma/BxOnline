package kr.co.scp.ccp.iotuser.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotUsrCDTO{
	@JsonIgnore
	private String dymmy;
//	private String custSeq;
	private String usrLoginId;
	private String usrNm;
	private String smsRcvNo;
	private String roleCdId ;
	private String roleCdNm ;
	private String useYn;
	private String orgSeq;
	private String orgNm;
	private String usrPhoneNo;
	private String usrEmail;
	private String regUsrId;

}
