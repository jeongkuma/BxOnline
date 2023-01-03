package kr.co.scp.ccp.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TbIotTreeStatusDTO {
	
	@JsonIgnore
	private String dummy;
	private boolean opened;
	private boolean selected;
	private String useYn;
	
	public TbIotTreeStatusDTO() {
		this.opened = false;
		this.selected = false;		
	}
	
	public TbIotTreeStatusDTO(boolean status) {
		this.opened = status;
	}
	
}