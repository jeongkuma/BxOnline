package kr.co.scp.ccp.iotOrg.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotOrgRDTO {
	@JsonIgnore
	private String dymmy;
	public String orgSeq  ;
	public String orgNm   ;
	public String orgLvl  ;
	public String orgOrder;
	public String useYn   ;
	public String orgPath ;
	public String upOrgSeq;
	public String upOrgNm;

}
