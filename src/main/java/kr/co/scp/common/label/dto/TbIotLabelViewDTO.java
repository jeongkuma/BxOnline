package kr.co.scp.common.label.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotLabelViewDTO {
	@JsonIgnore
	private String dymmy;
	private String id;
	private String text;
}
