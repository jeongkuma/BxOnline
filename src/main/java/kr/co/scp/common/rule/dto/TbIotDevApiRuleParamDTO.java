package kr.co.scp.common.rule.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotDevApiRuleParamDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Integer apiSeq;
	private Integer devSeq;
	private String callApiSeq;
	private String regUsrId;

}
