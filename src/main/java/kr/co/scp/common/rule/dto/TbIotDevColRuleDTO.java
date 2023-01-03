package kr.co.scp.common.rule.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotDevColRuleDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;

	private List<TbIotDevColRuleParamDTO> dataList;

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
	private String ruleDevType;
	private String ruleArgs;
	private String ruleRequest;
	private String ruleSourcekey;
	private String exeFile;
	private String devMdlCd;
	private String devClsCd;
	private String apiNm;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

	private String charSet  ;
	private String runGubun;

	private String parsingTcpRule;
	private String validRule;
	private String parsingRule;
	private String mappingRule;
	private String serviceCall;

	private String serverGubun;

	private String ruleMsgTypeCopy;

}
