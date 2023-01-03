package kr.co.scp.ccp.iotDev.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevDetSetParamDTO extends IoTBaseDTO {

	@JsonIgnore
	private String dymmy;
	public List<TbIotDevDetSetDTO> tbIotDevDetSetDTOList;
}// 장비 유형

