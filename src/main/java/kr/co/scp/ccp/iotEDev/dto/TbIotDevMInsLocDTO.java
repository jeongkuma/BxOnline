package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevMInsLocDTO extends IoTBaseDTO  {
	@JsonIgnore
	private String dymmy;
	private Integer eDevInsLocSeq;
	//private String instSiteSeq;
	private String entrDevSeq;
	private String instAddr;
	private String instLat;
	private String instLon;

}

