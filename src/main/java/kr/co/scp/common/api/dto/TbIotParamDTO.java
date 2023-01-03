package kr.co.scp.common.api.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotParamDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String apiParamSeq;
	private String apiSeq;
	private String paramGubun;
	private String paramKey;
	private String paramDesc;
	private String requiredYn;

	private String mode;
}
