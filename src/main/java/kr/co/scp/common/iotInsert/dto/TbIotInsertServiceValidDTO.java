package kr.co.scp.common.iotInsert.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertServiceValidDTO extends IoTBaseDTO implements Cloneable{
	@JsonIgnore
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
//	private String modDttm;

	// valid
	private String apiSeq;
	private String devSeq;
	private String valRuleSeq;
	private String valRuleName;
	private String ruleKind;
	private String ruleMsgType;
	private String defaultYn;
	private String nullYn;
	private String emptyYn;
	private String dataType;
	private String dataSize;
	private String dataMin;
	private String dataMax;
	private String allowRegex;
	private String notallowRegex;

	// 깊은 복사 함수
	public Object clone(){ Object obj = null; try{ obj = super.clone(); }catch(Exception e){} return obj; }

}
