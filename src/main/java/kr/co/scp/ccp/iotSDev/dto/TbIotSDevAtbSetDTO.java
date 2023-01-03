package kr.co.scp.ccp.iotSDev.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 고객별 장비속성 SET Table
 * @author pkjean
 *
 */
@Getter @Setter @ToString
public class TbIotSDevAtbSetDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String sDevAttbSetSeq;
	private String sDevAttbSeq;

	private String setParentCdId;
	private String devAttbSetCdId;
	private String devAttbSetCdNm;
	private String devAttbSetVal;

	private String svcCd;
	private String statusCd;

}
