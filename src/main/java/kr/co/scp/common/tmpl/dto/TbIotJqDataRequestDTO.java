package kr.co.scp.common.tmpl.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotJqDataRequestDTO {
	@JsonIgnore
	private String dymmy;
	   private String langSet;
	   private String progId;
	   private String tmpCdId;
	   private String devClsCdId;
	   private String custSeq;

}
