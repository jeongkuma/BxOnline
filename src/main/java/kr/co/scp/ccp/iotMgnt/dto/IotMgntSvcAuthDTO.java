package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSvcAuthDTO {

	@JsonIgnore
	private String dymmy;
	private String roleSeq;
	private String roleCdId;
	private String svcCd;
	private String menuProgSeq;
	private String menuProgGubun;

}
