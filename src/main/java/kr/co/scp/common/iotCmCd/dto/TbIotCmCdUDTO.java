package kr.co.scp.common.iotCmCd.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCmCdUDTO {
	@JsonIgnore
	private String dymmy     ;
	private String cdSeq     ;
	private String charSet   ;
	private String langSet   ;
	private String cdId      ;
	private String cdNm      ;
	private String cdDesc    ;
	private String cdType    ;
	private String useYn     ;
	private String useYnVal  ;
	private String parentCdId;
	private String parentCdNm;
	private String level1    ;
	private String level2    ;
	private String firstCdId ;
	private String paramKey  ;
	private String paramVal  ;
	private String modUsrId  ;
	private int lvl       ;
}
