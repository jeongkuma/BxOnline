package kr.co.scp.ccp.iotStatReport.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotComCollStatDTO {
	@JsonIgnore
	private String dymmy;
	// 통신/수집성공률 테이블
	private String commCollStatSeq;
	private String procDate;
	private String entrDevSeq;
	private String custLoginId;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String commRate;
	private String commSuccCnt;
	private String commmFailCnt;
	private String collRate;
	private String collSuccCnt;
	private String collFailCnt;
	private String modDttm;
	private String modUsrId;
	private String regUsrId;
	private String regDttm;

	// 통계 날짜별 통신율/수집율 (최대 7일)
	private String collCnt0;
	private String commRate0;
	private String collRate0;
	private String collSuccCnt0;
	private String collFailCnt0;
	private String collCnt1;
	private String commRate1;
	private String collRate1;
	private String collSuccCnt1;
	private String collFailCnt1;
	private String collCnt2;
	private String commRate2;
	private String collRate2;
	private String collSuccCnt2;
	private String collFailCnt2;
	private String collCnt3;
	private String commRate3;
	private String collRate3;
	private String collSuccCnt3;
	private String collFailCnt3;
	private String collCnt4;
	private String commRate4;
	private String collRate4;
	private String collSuccCnt4;
	private String collFailCnt4;
	private String collCnt5;
	private String commRate5;
	private String collRate5;
	private String collSuccCnt5;
	private String collFailCnt5;
	private String collCnt6;
	private String commRate6;
	private String collRate6;
	private String collSuccCnt6;
	private String collFailCnt6;

	// 조회 결과 항목
	private String custNm;
	private String orgNm;
	private int gid;

	// 검색조건
	private String searchStartDttm;
	private String searchEndDttm;
	private List<String> searchDateList;

	private String orgSeq;
	private int devCount;
	private String svcCd;
	private String custSeq;

}
