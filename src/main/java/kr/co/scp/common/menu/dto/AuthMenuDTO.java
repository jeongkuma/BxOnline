package kr.co.scp.common.menu.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthMenuDTO {
	@JsonIgnore
	private String dymmy;
	private String menuSeq		;
	private String	menuCdId	;
	private String progUri		;
	private String prntMenuId	;
	private String menuLevel	;
	private Integer menuOrder	;
	private String menuCdNm		;

	// 조건을 위한 필드
	private String langSet		;
	private String roleCdId		;
	private String svcCd		;
}
