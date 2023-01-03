package kr.co.scp.common.tmpl.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CopyJqGridDto extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String tmplSeq;
	private String langSet;
	private String devClsCd;
	private String devClsCdNm;
	private String orgLangSet;
	private String orgDevClsCd;
	private String copyType;

	private Integer chkCnt;
	private String msg;
}
