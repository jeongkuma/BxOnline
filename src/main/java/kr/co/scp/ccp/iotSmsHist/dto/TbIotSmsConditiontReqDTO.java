package kr.co.scp.ccp.iotSmsHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSmsConditiontReqDTO {
	@JsonIgnore
	private String dymmy;
	//메세지 유형
	private String messegeType;

	//등급
	private String grade;
}
