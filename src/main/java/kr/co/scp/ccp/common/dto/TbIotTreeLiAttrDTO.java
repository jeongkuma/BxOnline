package kr.co.scp.ccp.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotTreeLiAttrDTO {
	@JsonIgnore
	private String dymmy;
	public String li_attr; 
	
	public TbIotTreeLiAttrDTO() {
		this.li_attr = "";
	}
}