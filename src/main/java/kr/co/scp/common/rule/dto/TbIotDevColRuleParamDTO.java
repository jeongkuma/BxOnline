package kr.co.scp.common.rule.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotDevColRuleParamDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String ruleUri;
	private Integer apiSeq;
	private Integer devSeq;
	private String ruleMsgType;
	private String ruleColType;
	private Integer devruleSeq;
	private Integer ruleSeq;
	private String ruleGubun;
	private String ruleKind;
	private String ruleBytePosi;
	private String ruleBitPosi;
	private String ruleNumber;
	private String rulePoint;
	private String ruleTargetkey;
	private String ruleArgs;
	private String ruleRequest;
	private String ruleSourcekey;
	private String ruleDevType;
	private String exeFile;
	private String devMdlCd;
	private String regUsrId;

}
