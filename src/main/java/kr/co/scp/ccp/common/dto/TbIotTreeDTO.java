package kr.co.scp.ccp.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotTreeDTO {
	@JsonIgnore
	private String dymmy;
	public String id     ;
	public String parent ;
	public String text   ;
	public TbIotTreeDataDTO data;
	public TbIotTreeLiAttrDTO li_attr;
	public TbIotTreeStatusDTO state;
}