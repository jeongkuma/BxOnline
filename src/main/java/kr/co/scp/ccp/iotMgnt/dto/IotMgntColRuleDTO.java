package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntColRuleDTO {

	@JsonIgnore
	private String dymmy;
	private String ruleUri;
	private String devruleSeq;
	private String apiSeq;
	private String devSeq;
	private String ruleMsgType;
	private String ruleColType;
	private String ruleDevType;
	private String ruleSeq;
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
	private String exeFile;
	private String devMdlCd;
	private String regUsrId;

}
