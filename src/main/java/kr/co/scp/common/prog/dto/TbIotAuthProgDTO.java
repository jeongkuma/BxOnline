package kr.co.scp.common.prog.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotAuthProgDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Integer roleSeq;
	private String roleCdId;
	private String svcCd;
	private Integer progSeq;
	private String progNm;
	private String uiPath;
	private String progUri;

	// 조회를 위한 변수
	private String menuProgGubun;

}
