package kr.co.scp.ccp.iotSDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 고객별장비속성 테이블
 * @author pkjean
 *
 */
@Getter @Setter @ToString
public class TbIotSDevAtbDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String sDevAttbSeq;
	private String sDevSeq;

	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String svcCd;

	private String setParentCdId;
	private String devAttbCdId;
	private String devSetAttbCdId;
	private String devAttbCdNm;
	private String inputType;

	private String statusCd;

	private String paramKey;
	private String colNmCd;
	private String conNmCd;
	private String staAvgNmCd;
	private String staSumNmCd;

	private String detNmCd;
	private String mapYn;
	private String unit;
	private String orderNo;

	private List<TbIotSDevAtbSetDTO> svcDevAtbSets;

	private String langSet;
}
