package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 가입별장비현재값
 * @author pkjean
 */
@Getter @Setter @ToString
public class EDevCurValDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	//private Integer entrDevAttbSeq;
	private String eDevCurValSeq;
	private String entrDevSeq;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devAttbCdId;
	private String devAttbCdNm;
	private String devAttbType;
	private String inputType;
	private String inputValue;
	private String eDevDetSetSeq;
	private String pamKey;
	private String devVal;
	private String detStatusCdId;
	private String detStatusCdNm;
	private String staAvgVal;
	private String staCnt;
	private String devChgStatus;
	private String devChgValue;
	private String devTime;
}
