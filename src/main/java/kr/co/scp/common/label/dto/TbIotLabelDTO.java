package kr.co.scp.common.label.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotLabelDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private Integer labelSeq;
	private String langSet;
	private String id;
	private String uuId;
	private String text;
	private String langType;


}
