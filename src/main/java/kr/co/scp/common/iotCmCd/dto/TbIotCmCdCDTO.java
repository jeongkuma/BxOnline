package kr.co.scp.common.iotCmCd.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCmCdCDTO {
	@JsonIgnore
	private String dymmy;
	private String cdSeq        ;
	private int lvl          ;
	private String charSet   ;
	private String langSet   ;
	private String cdId      ;
	private String cdNm      ;
	private String cdDesc    ;
	private String useYn     ;
	private String parentCdId;
	private String firstCdId ;
	private String topId     ;
	private String paramKey  ;
	private String paramVal  ;
	private String desc      ;
	private String regUsrId  ;

}
