package kr.co.scp.ccp.iotOrg.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotOrgOptDTO {
	@JsonIgnore
	private String dymmy;
	public String orgSeq  ;
	public String orgNm   ;
	public String orgPath ;
	public String orgLvl  ;
}
