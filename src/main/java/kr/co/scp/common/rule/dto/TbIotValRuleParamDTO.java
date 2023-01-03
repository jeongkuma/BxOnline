package kr.co.scp.common.rule.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotValRuleParamDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String	ruleUri;
	private Integer	devSeq;
	private Integer	valRuleSeq;
	private String	valRuleName;
	private String	ruleKind;
	private String	ruleMsgType;
	private String	defaultYn;
	private String	nullYn;
	private String 	emptyYn;
	private String  dataType;
	private Integer dataSize;
	private Integer dataMin;
	private Integer dataMax;
	private String  allowRegex;
	private String  notallowRegex;
	private String  regUsrId;
//	private String  regDttm;
	private String  modUsrId;
	private String  modDttm;
	private Integer apiSeq;
	private String  ruleColType;
	private String  ruleSourcekey;
	private String  ruleRequest;
	private String  cdNm;

}
