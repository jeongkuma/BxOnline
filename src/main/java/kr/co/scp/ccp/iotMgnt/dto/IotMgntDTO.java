package kr.co.scp.ccp.iotMgnt.dto;

import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntDTO {
	@JsonIgnore
	private String dymmy;
	public ArrayList<?> param;
	public String svcCd;
	public String gubun;
	public String type;
	public String devMdlCd;
	public String devClsCd;
	public String langSet;
	public String useYn;
	public String statusCd;
}
