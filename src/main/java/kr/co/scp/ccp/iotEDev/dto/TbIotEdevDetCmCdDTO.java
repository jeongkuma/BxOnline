package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 장애sms  DTO
 * @author
 *
 */
@Setter @Getter @ToString
public class TbIotEdevDetCmCdDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String CdId;
	private String CdNm;
	private String[] CdIdList;
}
