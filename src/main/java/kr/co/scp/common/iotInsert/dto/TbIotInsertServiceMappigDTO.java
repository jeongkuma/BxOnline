package kr.co.scp.common.iotInsert.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertServiceMappigDTO extends IoTBaseDTO implements Cloneable{
	@JsonIgnore
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
//	private String modDttm;

	//mappig
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


	// 깊은 복사 함수
	public Object clone(){ Object obj = null; try{ obj = super.clone(); }catch(Exception e){} return obj; }

}
