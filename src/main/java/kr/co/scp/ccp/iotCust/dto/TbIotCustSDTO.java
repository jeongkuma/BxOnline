package kr.co.scp.ccp.iotCust.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCustSDTO{
	@JsonIgnore
	private String dymmy;
    private String custSeq ;
	private String custNm  ;
	private String svcCd   ;

}
