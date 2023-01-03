package kr.co.scp.ccp.iotSDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 서비스별 장비 정보
 * @author pkjean
 *
 */
@Getter @Setter @ToString
public class TbIotSDevDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String sDevAttbSeq;
	private String sDevSeq;
	private String svcSeq;

	private String svcCd;

	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;

	private String statusCd;

	private String parentDevSeq;
	private String vendorNm;

	private String langSet;
	/*
	 * private String abnormalIconFile; private String abnormalIconFile2;
	 *
	 * private String nomalIconFile; private String iconRegYn;
	 */

	private List<TbIotSDevAtbDTO> iotCustDevAtbs;

}
