package kr.co.scp.common.menu.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuJsonDTO {
	@JsonIgnore
	private String dymmy;
	private String menu_seq	;
	private String	menu_id		;
	private String	href		;
	private String prnt_menu_id	;
	private String menulevel	;
	private Integer sort		;
	private String title		;

}
