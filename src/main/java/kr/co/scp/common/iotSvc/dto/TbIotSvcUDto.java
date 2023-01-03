package kr.co.scp.common.iotSvc.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSvcUDto{
	@JsonIgnore
	private String dymmy;
	public String svcSeq    ;
	public String svcCd     ;
	public String svcCdNm   ;
	public String devClsCd  ;
	public String devClsNm  ;
	public String defaultIcon;
	public String stopIcon  ;
	public String errorIcon ;
	public String svcLvl    ;
	public String svcLevel  ;
	public String svcOrder  ;
	public String upSvcSeq  ;
	public String modUsrId  ;
	public String[] deleteArr;

}
