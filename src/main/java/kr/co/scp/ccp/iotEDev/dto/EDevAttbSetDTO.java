package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 장비속성Set
 * @author pkjean
 *
 */
@Getter @Setter
public class EDevAttbSetDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String cDevAttbSetSeq;
	private String cDevAttbSeq;

	private String devAttbCdId;
	private String entrDevAttbSetSeq;
	//private String entrDevAttbSeq;
	private String eDevCurValSeq;

	private String devAttbSetCdId;
	private String devAttbSetCdNm;

	private String statusCd;
	private String devAttbSetVal;

}
