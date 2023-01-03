package kr.co.scp.common.iotCmCd.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCmCdOptionDTO {
	@JsonIgnore
	private String dymmy;
	private String cdId      ;
	private String cdNm      ;
	private String parentCdId1;
	private String parentCdId2;
	private String parentCdId;
	private String charSet  ;
	private String langSet  ;
	private String cdOrder ;

}
