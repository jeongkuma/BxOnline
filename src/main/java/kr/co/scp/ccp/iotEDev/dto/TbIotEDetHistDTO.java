package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEDetHistDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	// 장애관리 > 장애현황
		private String devClsCd;
		private String devClsCdNm;
		private String devMdlCd;
		private String devMdlNm;
		private String custSeq;
		private String entrDevSeq;

		private String svcCd;
		private String specDvNm;

		private String langSet;
		private String charSet;
		private String progId;

		private int tmplSeq;
		private int displayRowCount;
		private int currentPage;
		private int startPage;
		private int endPage;

		private String colModelData;
		private String colNameData;

		private String colDate;
		private String ctn;
		private String usingNo;
		private String instAddr;
		private String instNo;
		private String machineNo;
		private String devUname;
		private String searchStartDttm;
		private String searchEndDttm;
		private String detStatusCd;		// 장애수준코드
		private String detStatusNm;		// 장애수준값
		private String commStatusCd;	// 통신상태코드
		private String commStatusNm;	// 통신상태값
		private String detSetDesc;		// 장애설명
		private String attbVl01;		// ATTB값 01
		private String attbVl02;		// ATTB값 02
		private String attbVl03;		// ATTB값 03
		private String attbVl04;		// ATTB값 04
		private String attbVl05;		// ATTB값 05
		private String attbVl06;		// ATTB값 06
		private String attbVl07;		// ATTB값 07
		private String attbVl08;		// ATTB값 08
		private String attbVl09;		// ATTB값 09
		private String attbVl10;		// ATTB값 10
		private String attbVl11;		// ATTB값 11
		private String attbVl12;		// ATTB값 12
		private String attbVl13;		// ATTB값 13
		private String attbVl14;		// ATTB값 14
		private String attbVl15;		// ATTB값 15
		private String attbVl16;		// ATTB값 16
		private String attbVl17;		// ATTB값 17
		private String attbVl18;		// ATTB값 18
		private String attbVl19;		// ATTB값 19
		private String attbVl20;		// ATTB값 20
		private String attbVl21;		// ATTB값 21
		private String attbVl22;		// ATTB값 22
		private String attbVl23;		// ATTB값 23
		private String attbVl24;		// ATTB값 24
		private String attbVl25;		// ATTB값 25
		private String attbVl26;		// ATTB값 26
		private String attbVl27;		// ATTB값 27
		private String attbVl28;		// ATTB값 28
		private String attbVl29;		// ATTB값 29
		private String attbVl30;		// ATTB값 30

		private String orgNm;	// 하위 조직 검색을 위한 파라미터 추가
}
