package kr.co.scp.common.menu.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuCdDTO {
	@JsonIgnore
	private String	dymmy;
	private String	svcCdId;
	private String	svcCdNm;
	private String	roleCdId;
	private String	parentCdId;
	private String	langSet;
}
