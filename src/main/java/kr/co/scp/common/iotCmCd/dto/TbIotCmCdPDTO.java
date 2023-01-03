package kr.co.scp.common.iotCmCd.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
public class TbIotCmCdPDTO{
	@JsonIgnore
	private String dymmy;
	private String cdSeq        ;
	private String cdOrder   ;
	private String charSet   ;
	private String langSet  ;
	private String cdId      ;
	private String cdNm      ;
	private String useYn     ;
	private String parentCdId;
	private String parentCdNm;
	private String firstCdId ;
	private String paramKey  ;
	private String paramVal  ;
	private int lvl ;

}
