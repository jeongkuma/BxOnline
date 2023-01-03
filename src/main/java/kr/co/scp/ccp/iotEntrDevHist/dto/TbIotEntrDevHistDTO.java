package kr.co.scp.ccp.iotEntrDevHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEntrDevHistDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	// 가입별장치수집이력
	private String colDate;
	private String curStsDate;
	private String custSeq;
	private String entrDevSeq;
	private String devClsCdId;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String ctn;
	private String entrNp;
	private String devUuid;
	private String fstRegDttm;
	private String tmpSeq;
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
	private String specDvNm;

	// 헤더 검색조건
	private String chartSet;
	private String progId;
	private String tmpCdId;

	//조건 검색
	private String selectedCodition;
	private String inputText;


	// 가입별 장비마스터
	private String usingNo;
	private String machineNo;
	private String instAddr;
	private String instNo;
	private String devBuildingNm;

	private String devUname;

}
