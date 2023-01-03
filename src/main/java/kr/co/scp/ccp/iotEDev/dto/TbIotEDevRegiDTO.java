package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 가입별 장비 등록 DTO
 * @author "pkjean"
 *
 */
@Setter @Getter @ToString
public class TbIotEDevRegiDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dummy;
	//가입별 장비 마스터
	private String entrDevSeq;
	private String eventDttm;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String svcCd;
	private String eventCode;
	private String iagEventCode;
	private String hldrCustNo;
	private String entrNo;
	private String aceno;
	private String chngBfrProdNo;
	private String prodNo;
	private String itemId;
	private String manfSerialNo;
	private String prevManfSerialNo;
	private String devEsn;
	private String custSeq;
	private String orgSeq;
	private String orgNm;
	private String devUuid;
	private String ctn;
	private String devUname;
	private String machineNo;
	private String usingNo;
	private String instNo;
	private String bootDtm;
	private String instDttm;
	private String colPeriod;
	private String colUnit;
	private String devBuildingNm;
	//2019-04-17 Add
	private String devImgPath;
	private String devImgFileNm;
	private String devRegiDt;
	//2019-04-26 Add
	private String sndPeriod;
	private String sndUnit;

	//장비설치위치
	private String eDevInsLocSeq;
	//private Integer instSiteSeq;
	private String instAddr;
	private String instAddrDetail;
	private String assistantAddr;
	private String instLat;
	private String instLon;

	//20190501
	private String statusCd;
	private String orgStatusCd;

	//장비속성 GET
	private List<EDevAttbDTO> entrDevAttbs;

	private String eDevCurValSeq;
	//가입속성 SET
	private List<EDevCurValDTO> entrDevCurValues;

	//중복체크할 대상 컬럼
	private String dupleType;

	// 중복체크를 위한 변수
	private String msg;

	private String langSet;

}
