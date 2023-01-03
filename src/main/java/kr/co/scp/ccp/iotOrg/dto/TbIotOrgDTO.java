package kr.co.scp.ccp.iotOrg.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TbIotOrgDTO {
	@JsonIgnore
	private String dymmy;
	public String orgSeq  ;
	public String custSeq ;
	public String orgNm   ;
	public String orgLvl  ;
	public String orgOrder;
	public String useYn   ;
	public String orgPath ;
	public String upOrgSeq;
	public String upOrgNm;
	public String regUsrId;
	public String regDttm ;
	public String modUsrId;
	public String modDttm ;
}
