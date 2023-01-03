package kr.co.scp.common.iotInsert.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertDevListDTO extends IoTBaseDTO {

	private String svcCd;
	private List<TbIotInsertDevMDTO> devM;
	private List<TbIotInsertDevAttbDTO> devAttb;
	private List<TbIotInsertDevAttbSetDTO> devAttbSet;
	private List<TbIotInsertDevDetSetDTO> devDetSet;

}
