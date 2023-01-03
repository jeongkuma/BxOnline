package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class EDevMdlDTO {
	@JsonIgnore
	private String dymmy;

	private String devMdlCd;

	private String devMdlNm;

}
