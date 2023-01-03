package kr.co.scp.ccp.iotCtrlHist.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCtrlHistDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	//제어이력 테이블
	private Integer ctrlHistSeq;
	private Integer ctrlRsvSeq;
	private Integer custSeq;

	private List<TbIotCtrlHistDTO> ctrList;
	private String custNm;
	private String entrDevSeq;
	private String devClsCdId;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String proDate;
	private String entrNo;
	private String ctn;
	private String entityId;
	private String devAttrCdId;
	private String devAttrCdNm;
	private String pamKey;
	private String devVal;
	private String col;
	private String prcCd;
	private String ctlDate;
	private String ctlTypeNm;
	private String curDevVal;
	private String curDevltoDevVal;
	private String devTime;
	private String resDate;
	private String instAddr;
	private String modUsrId;

	private String colNm;
	private String prcCdNm;

	private String retryCnt;

	//제어예약 예약일자
	private String requestDate;

	//기간검색 변수
	private String searchStartDttm;
	private String searchEndDttm;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

//	private String attbVl01;
//	private String attbVl02;
//	private String attbVl03;
//	private String attbVl04;
//	private String attbVl05;
//	private String attbVl06;
//	private String attbVl07;
//	private String attbVl08;
//	private String attbVl09;
//	private String attbVl10;
//	private String attbVl11;
//	private String attbVl12;
//	private String attbVl13;
//	private String attbVl14;
//	private String attbVl15;
//	private String attbVl16;
//	private String attbVl17;
//	private String attbVl18;
//	private String attbVl19;
//	private String attbVl20;
//	private String attbVl21;
//	private String attbVl22;
//	private String attbVl23;
//	private String attbVl24;
//	private String attbVl25;
//	private String attbVl26;
//	private String attbVl27;
//	private String attbVl28;
//	private String attbVl29;
//	private String attbVl30;

	// 상위 조직 제외 검색 조건
	private String orgNm;
	// 식별명,사용자번호 필드 추가
	private String devUname;
	private String usingNo;
	// 조회구분 추가
	private String searchType;
	private String searchKey;


}
