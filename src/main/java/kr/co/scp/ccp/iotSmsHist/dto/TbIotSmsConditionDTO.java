package kr.co.scp.ccp.iotSmsHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSmsConditionDTO {
	@JsonIgnore
	private String dymmy;
	//메세지 유형
	private String trEtc1;
	private String trEtc1Nm;

	//등급
	private String trEtc2;
	private String trEtc2Nm;
}
