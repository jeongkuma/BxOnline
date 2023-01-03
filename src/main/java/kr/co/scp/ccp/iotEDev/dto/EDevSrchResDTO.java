package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.PageDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class EDevSrchResDTO extends PageDTO {
	@JsonIgnore
	private String dymmy;
	private List<TbIotEDevDTO> dataList;

	public String  msg;

}

