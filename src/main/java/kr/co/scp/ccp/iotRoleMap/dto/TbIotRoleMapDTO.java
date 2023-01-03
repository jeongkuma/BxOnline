package kr.co.scp.ccp.iotRoleMap.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter @Getter @ToString
public class TbIotRoleMapDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String roleSeq		;
	private String roleCdId		;
	private String roleCdNm		;
	private String menuProgSeq	;
	private String menuProgGubun;
	private String menuProgNm	;
	private String txYn			;

	// 역할 부모코드
	private String parentRoleCd	;

	// 서비스 부모코드
	private String parentSvcCd	;

	private String svcCd		;
	private String svcNm		;

	// 조회 조건 변수
	private String inputValue		;
	private String roleType			;
	private String svcType			;
	private String gubunType		;
	private Integer displayRowCount	;
	private Integer currentPage		;
	private Integer startPage		;
	private boolean flag				;

	// 공통코드 언어셋
	private String langSet		;

	private List<String> roleSeqList;
	private List<TbIotRoleMapDTO> insertDto;
}
