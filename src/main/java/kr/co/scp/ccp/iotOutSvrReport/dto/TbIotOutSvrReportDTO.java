package kr.co.scp.ccp.iotOutSvrReport.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter
@Getter
public class TbIotOutSvrReportDTO extends IoTBaseDTO {
    @JsonIgnore
    private String dymmy;
    private Long outSvrInfoSeq;
    private Long outSvrSeq;
    private String custSeq;
    private String custNm;
    private String svcCd;
    private String serverNm;
    private String inoutCd;
    private String tranCd;
    private String protocol;
    private String proxyYn;
    private String ip;
    private int port;
    private String certKey;
    private String useYn;

    private String outStatDay;
    private int entrDevSeq;
    private String devClsCd;
    private String devClsNm;
    private String devMdlCd;
    private String devMdlNm;
    private String ctn;
    private String usingNo;
    private String outSvrCd;
    private String svcNm;
    private String devUuid;
    private String devUname;
    private int sendSuccessCnt;
    private int sendFailCnt;
    private int receiveSuccessCnt;
    private int receiveFailCnt;
    private String sendRst;
    private String receiveRst;
    private String sendReceiveRst;
    private String status;
    private String statusYn;
    private String tranNm;
    private String outSvrNm;

    private String successYn;
    private String cdNm;
    private String resMsg;
    private int outMsgHistSeq;
    private int outMsgHSubSeq;
    private String reqMsg;
    private String resDttm;
    private String reqDttm;
    private String firstDttm;

    private String fromServerNm;
    private String toServerNm;

    // 가동률
    private String orDay;
    private String opr;
    private String oprCount;
    private int tot;
    private int totCnt;
    private int oprCnt;
    private String colTot;
    private String colDevCls;
    private String colDevMdl;
    private String totOpr;
    private int sumOprCnt;
    private int sumTotCnt;

    //내부서버연동
    private String colPassingYn;
    private String colDataSaveYn;
    private String colAccuYn;
    private String colSympYn;
    private String colOutYn;
    private String colSvrSeq;
    private String selectOneCustSeq;
    private String selectOneSvcCd;
    private String selectOneDevClsCd;
    private String selectOneDevMdlCd;

    // 단일 고객사 조회 변수
    private String roleCdId;

    // 페이징 변수
    private Integer displayRowCount;
    private Integer currentPage	;
    private Integer startPage	;

    // 공통코드 언어셋
    private String langSet		;

	private String charSet;
	private String progId;
	private String orgNm;

	// 검색조건
	private String searchStartDttm;
	private String searchEndDttm;
	private List<String> searchDateList;
	private String startDate;
	private String endDate;
	//일단위 검색
	private String searchDate;

	private String colNameData;
	private String colModelData;
	private String tmplSeq;
	private String progSeq;
	private String colOrder;

	// 엑셀 다운로드
	private String excelFlag;
	private String popUpYn;

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
