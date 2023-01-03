package kr.co.scp.ccp.iotRoleMap.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class RoleCdListDTO {
	@JsonIgnore
	private String dymmy;
	private String roleCdId		;
	private String roleCdNm		;
}
