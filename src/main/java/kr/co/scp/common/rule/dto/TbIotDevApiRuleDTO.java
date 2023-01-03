package kr.co.scp.common.rule.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotDevApiRuleDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;

	private List<TbIotDevApiRuleParamDTO> dataList;

	private Integer apiSeq;
	private Integer devSeq;
	private Integer callApiSeq;
	private String regUsrId;

	private Integer cdId;
	private Integer cdNm;


}
