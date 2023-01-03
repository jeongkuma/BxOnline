package kr.co.scp.ccp.iotuser.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotUsrRDTO {
	@JsonIgnore
	private String dymmy;
	private String usrSeq;
	private String usrLoginId;
	private String usrNm;
	private String usrPwd;
	private String smsRcvNo;
	private String roleCdId ;
	private String roleCdNm ;
	private String useYn;
	private String orgSeq;
	private String orgNm;
	private String usrPhoneNo;
	private String usrEmail;

}
