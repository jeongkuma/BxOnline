package kr.co.scp.ccp.iotSelDevice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotEntrDevMDTO {
	@JsonIgnore
	private String dymmy;
	private String entrDevSeq;
	private String eventDttm;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String hldrCustNo;
	private String statusCd;
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
	private String devUuid;
	private String ctn;
	private String devUname;
	private String machineNo;
	private String usingNo;
	private String instNo;
	private String devImgPath;
	private String devImgFileNm;
	private String bootDtm;
	private String devRegiDt;
	private String colPeriod;
	private String colUnit;
	private String sndPeriod;
	private String instDttm;
	private String sndUnit;
	private String imgFile;

	private String instAddr;
	private String instLat;
	private String instLon;
	private String svcCd;
	private String langSet;

	private String attbStatusCd;

	// 상위 조직 제외 검색 조건을 위한 필드 추가 20190905
	private String orgNm;

	// IAG 연동을 위해 추가됨 20200618
	private String devBuildingNm;
	private String regUsrId;
	private String regDttm;
	private String modUsrId;
	private String modDttm;
}
