package kr.co.scp.ccp.iotEntrDevCol.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotEDevColValDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	// 가입별장치수집이력
	private Integer entrDevSeq;
	private String curStsDate;

	private String svcCd;
	private String custSeq;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devBuildingNm;
	private String instNo;
	private String ctn;
	private String entrNo;
	private String usingNo;
	private String devUuid;
	private String devUname;
	private String instAddr;
	private String instAddrDetail;
	private String instLat;
	private String instLon;
	private String fstRegDttm;
	private Integer tmplSeq;

	private String attbVl01;
	private String attbVl02;
	private String attbVl03;
	private String attbVl04;
	private String attbVl05;
	private String attbVl06;
	private String attbVl07;
	private String attbVl08;
	private String attbVl09;
	private String attbVl10;
	private String attbVl11;
	private String attbVl12;
	private String attbVl13;
	private String attbVl14;
	private String attbVl15;
	private String attbVl16;
	private String attbVl17;
	private String attbVl18;
	private String attbVl19;
	private String attbVl20;
	private String attbVl21;
	private String attbVl22;
	private String attbVl23;
	private String attbVl24;
	private String attbVl25;
	private String attbVl26;
	private String attbVl27;
	private String attbVl28;
	private String attbVl29;
	private String attbVl30;

	// 검색조건
	private String searchType;
	private String searchKey;
	private String startDate;
	private String endDate;
	private String searchStartDttm;
	private String searchEndDttm;
	private int startPage;
	private int displayRowCount;
	private int currentPage;

	public String msg;
	public String charSet;

	private String progId;
	private String progSeq;
	private String tmplCdId;
	private String orgNm;
	private String kpmNum;
}
