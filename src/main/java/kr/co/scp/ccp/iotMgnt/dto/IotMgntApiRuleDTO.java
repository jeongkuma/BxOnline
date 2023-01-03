package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntApiRuleDTO {

	@JsonIgnore
	private String dymmy;
	private String apiSeq;
	private String devSeq;
	private String callApiSeq;
	private String regUsrId;

}
