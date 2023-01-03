package kr.co.scp.common.rule.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIotRuleParamDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Integer ruleSeq;
	private String rule_gubun;
	private String ruleGubun;
	private String charSet;
	private String ruleName;
	private String ruleType;
	private String userId;
	private String time;

}
