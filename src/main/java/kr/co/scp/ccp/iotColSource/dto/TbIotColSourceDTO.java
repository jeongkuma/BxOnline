package kr.co.scp.ccp.iotColSource.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotColSourceDTO {

	@JsonIgnore
	private String dymmy;

	// 수집원문정보
	private String colDate;
	private Integer colSeq;
	private String logKey;
	private String errCode;
	private String bodyInfo;
	private String headerInfo;
	private String regDttm;
	private Integer retryCnt;
	private String colStateCd;
	private Integer entrDevSeq;
	private String colProcDesc;
	private String ackProcDesc;
	
	// 가입별장치수집이력
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
//	private String devMdlNm;
	private String ctn;
	private String devUuid; // entityId
	private String custSeq;


	// 검색조건
	private String searchType;
	private String searchKey;
	private String statusSearchType;
	private String startDate;
	private String endDate;
	
	private int startPage;
	private int displayRowCount;
	private int currentPage;

}
