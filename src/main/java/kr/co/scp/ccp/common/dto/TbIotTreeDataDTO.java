package kr.co.scp.ccp.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotTreeDataDTO {
	@JsonIgnore
	private String dymmy;
	public String selfSeq ;
	public String parent ;
	
}