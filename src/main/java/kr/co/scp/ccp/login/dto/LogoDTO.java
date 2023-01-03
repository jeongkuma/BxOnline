package kr.co.scp.ccp.login.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class LogoDTO {
	@JsonIgnore
	private String dymmy;

	private String	userPlatform	;
	private String	custSeq			;
	private Integer	contentSeq		;
	private String	contentType		;
	private String	custWlogoFile	;
	private String	custMlogoFile	;
	private String	fileName		;
	private String	filePath		;
}
