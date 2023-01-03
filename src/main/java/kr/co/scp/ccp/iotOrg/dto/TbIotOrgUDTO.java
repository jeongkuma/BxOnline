package kr.co.scp.ccp.iotOrg.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotOrgUDTO {
	@JsonIgnore
	private String dymmy;
	public String orgSeq  ;
	public String custSeq ;
	public String orgNm   ;
	public String orgLvl  ;
	public String originOrder;
	public String orgOrder;
	public String useYn   ;
	public String orgPath ;
	public String upOrgSeq;
	public String upOrgNm ;
}
