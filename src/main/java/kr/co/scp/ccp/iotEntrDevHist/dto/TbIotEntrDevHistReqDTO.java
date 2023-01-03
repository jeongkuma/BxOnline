package kr.co.scp.ccp.iotEntrDevHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEntrDevHistReqDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	// 가입별장치수집이력
	private String custSeq;
	private String langSet;

}
