package kr.co.scp.ccp.iotRoleMap.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class ProgListDTO {
	@JsonIgnore
	private String dymmy;
	private String progSeq		;
	private String progNm		;
	private String uiPath		;

	private boolean checked		;
}
