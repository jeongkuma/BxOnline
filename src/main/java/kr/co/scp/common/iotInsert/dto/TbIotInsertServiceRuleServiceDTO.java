package kr.co.scp.common.iotInsert.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertServiceRuleServiceDTO extends IoTBaseDTO implements Cloneable{
	@JsonIgnore
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
//	private String modDttm;

	// ruleservice
	private String devSeq;
	private String apiSeq;
	private String callApiSeq;

	// 깊은 복사 함수
	public Object clone(){ Object obj = null; try{ obj = super.clone(); }catch(Exception e){} return obj; }

}
