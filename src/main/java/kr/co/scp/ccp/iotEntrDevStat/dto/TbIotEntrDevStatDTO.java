package kr.co.scp.ccp.iotEntrDevStat.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEntrDevStatDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	// 장비 유형 및 장비 모델 목록 조회
	private Integer custSeq;		// 고객 순번
	private String devClsCd;		// 장비 유형 코드
	private String devClsCdNm;		// 장비 유형 이름
	private String devMdlCd;		// 장비 모델 코드
	private String devMdlNm;		// 장비 모델 이름

	// 템플릿 헤더 동적 조회
//	private String colModelData;	// 컬럼 헤더명
//	private String colNameData;		// 컬럼 데이터

	// 고정 조회 컬럼
	private Integer entrDevSeq;		// 가입별 장비 순번
	private String instAddr;		// 주소
	private String devUuid;			// 장비 UUID
	private String ctn;				// CTN
	private String machineNo;		// 기물번호
	private String usingNo;			// 사용번호
	private String instNo;			// 설치번호

	// 동적 조회 컬럼 : 통계 조회
	private String statType;		// 합계,평균 통계구분
	private String timeGubun;		// 시간,일,주,월 구분
	private String statDate;		// 통계일자
	private String timeColDate;		// 시간통계일자
	private String dayColDate;		// 일통계일자
	private String weekStatDate;	// 주통계일자
	private String monStatDate;		// 월통계일자
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

	// 기간 검색
	private String searchStartDttm;
	private String searchEndDttm;

	// else
	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

	// jqGrid
	private String langSet;
	private String progId;
	private String tmpCdId;
	private String headerDevClsCdId;
}
