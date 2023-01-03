package kr.co.scp.ccp.iotSmsHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSmsTranHistDTO {
	@JsonIgnore
	private String dymmy;
	private String trNum;
	private String trSendDate;
	private String trSendStat;
	private String trMsgType;
	private String trMsgTypeNm;
	private String trPhone;
	private String trCallback;
	private String trMsg;
	private String trId;
	private String trRsltstat;
	private String trRsltdate;
	private String trModified;
	private String trNet;
	private String trEtc1;
	private String trEtc1Nm;
	private String trEtc2;
	private String trEtc2Nm;
	private String trEtc3;
	private String trEtc4;
	private String trEtc5;
	private String trEtc6;
	private String trRealsenddate;
	private String trRouteId;

	private String searchKind;
	private String custNm;
	private String custLoginId;
	private int sendCnt;
	private int successCnt;
	private int failCnt;

	//기간검색 변수
	private String searchStartDttm;
	private String searchEndDttm;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;
	private Integer endPage;

	private String roleCdId;
	private String custSeq;


}

