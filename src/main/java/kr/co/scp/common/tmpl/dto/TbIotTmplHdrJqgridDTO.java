package kr.co.scp.common.tmpl.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotTmplHdrJqgridDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String tmplHdrAttbSeq;
	private String tmplSeq;
	private String sDevSeq;
	private String colNameData;
	private String colModelData;
	private String langSet;
	private String tmp1;
	private String tmp2;
	private String tmp3;
	private String devClsCdNm;
	private String devClsCd;
	private String colOrder;
	private String mode;
}
