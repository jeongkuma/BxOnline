package kr.co.scp.common.iotSvc.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSvcCombinedDto{
	@JsonIgnore
	private String dymmy;
	// properties for svc
	public String svcSeq    ;
	public String svcCd     ;
	public String svcCdNm   ;
	public String devClsCd  ;
	public String devClsNm  ;
	public String defaultIcon;
	public String stopIcon  ;
	public String errorIcon ;
	public String multiIcon ;
	public String multiErrIcon ;
	public String grpDefIcon;
	public String grpStpIcon;
	public String grpErrIcon;
	public String svcLvl    ;
	public String svcLevel  ;
	public String svcOrder  ;
	public String upSvcSeq  ;
	public String regUsrId  ;
	public String regDttm   ;
	public String modUsrId  ;
	public String[] deleteArr;
	public String[] deleteCdList;

	// properties for cmcd
	public String cdSeq     ;
	public String cdDesc    ;
	public String cdId      ;
	public String cdNm      ;
	public String charSet   ;
	public String langSet   ;
	public String firstCdId ;
	public String lvl       ;
	public String parentCdId;
	public String topCdId   ;
	public String useYn     ;

}
