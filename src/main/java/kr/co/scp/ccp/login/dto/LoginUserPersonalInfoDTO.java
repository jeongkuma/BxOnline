package kr.co.scp.ccp.login.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class LoginUserPersonalInfoDTO {
	@JsonIgnore
	private String dymmy;
	private String	custLoginId		;
	private String	usrLoginId		;
	private String	usrNm			;
	private String	smsRcvNo		;
	private String	usrPhoneNo		;
	private String	usrEmail		;
}
