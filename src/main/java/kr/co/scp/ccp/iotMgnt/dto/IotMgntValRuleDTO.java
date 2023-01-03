package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntValRuleDTO {

	@JsonIgnore
	private String dymmy;
	private String ruleUri;
	private String devSeq;
	private String valRuleSeq;
	private String valRuleName;
	private String ruleKind;
	private String defaultYn;
	private String nullYn;
	private String emptyYn;
	private String dataType;
	private String dataSize;
	private String dataMin;
	private String dataMax;
	private String allowRegex;
	private String notallowRegex;
	private String regDttm;
	private String modUsrId;
	private String modDttm;
	private String apiSeq;
	private String ruleColType;
	private String ruleSourcekey;
	private String ruleRequest;
	private String cdNm;
	private String ruleMsgType;
	private String regUsrId;

}
