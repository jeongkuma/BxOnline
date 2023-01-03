package kr.co.scp.ccp.iotEDev.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEDevDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	//가입장비 마스터
	private String entrDevSeq;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String svcCd;
	private String hldrCustNo;
	private String statusCd;
	private String statusNm;
	private String eventCode;
	private String iagEventCode;
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
	private String devBuildingNm;
	private String devImgPath;
	private String devImgFileNm;
	private String bootDtm;
	private String devRegiDt;
	private String colPeriod;
	private String colUnit;
	private String sndPeriod;
	private String sndUnit;
	private String instDttm;
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
//	private String modDttm;

	//가입장비 위치정보
	private String eDevInsLocSeq;
	//private String instSiteSeq;
	private String instAddr;
	private String instAddrDetail;
	private String instLat;
	private String instLon;

	//검색조건
	private String searchType;
	private String searchKey;
	private String startDate;
	private String endDate;
	private int startPage;
	private int displayRowCount;
	private int currentPage;

	public String msg;
	public String charSet;


}

