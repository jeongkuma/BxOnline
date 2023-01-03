package kr.co.scp.ccp.iotMgnt.dto;

import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntRoleMapDTO {
	@JsonIgnore
	private String dymmy;
	public ArrayList<?> param;
	private String roleSeq;
	private String roleCdId;
	private String svcCd;
	private String menuProgSeq;
	private String menuProgGubun;
	private String regUsrId;
}
