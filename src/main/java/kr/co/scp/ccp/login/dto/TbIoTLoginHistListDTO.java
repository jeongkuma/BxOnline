package kr.co.scp.ccp.login.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTLoginHistListDTO {
	@JsonIgnore
	private String dymmy;
		private double loginHistSeq		;
		private String histRegDttm		;
		private String loginSuccYn		;
		private String clientIp			;
		private String custLoginId		;
		private String usrLoginId		;
		private String loginFailReason	;
		private String usrOs			;
		private String usrBrowser		;
}
