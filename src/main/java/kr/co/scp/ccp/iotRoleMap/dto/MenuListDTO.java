package kr.co.scp.ccp.iotRoleMap.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class MenuListDTO {
	@JsonIgnore
	private String dymmy;
	private String menuSeq		;
	private String menuCdId		;
	private String menuCdNm		;
	private String menuLevel	;
	private String upMenuSeq	;
	private String menuOrder	;
	private String useYn		;
	private String langSet		;
	private String svcCd		;
}
