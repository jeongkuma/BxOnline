package kr.co.scp.ccp.iotOutMessage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter @Getter
public class TbIotOutSvrMsgSetMDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Long outMsgSetSeq;
	private Long outMsgSetHSeq;
	private Long outSvrSeq;
	private String custSeq;
	private String custNm;
	private String svcCd;
	private String svcNm;
	private String devClsCd;
	private String devMdlCd;
	private String devClsCdNm;
	private String devMdlCdNm;
	private String tranCd;
	private String tranCdNm;
	private String uri;
	private String medth;
	private String head;
	private String body;
	private String callBackUri;
	private String callBackMedth;
	private String callBackHead;
	private String callBackBody;
	private String serverNm;
	private String byPassYn;
	private Integer retryMin;
	private String retryTm;
	private String regUsrId;

	 // 페이징 변수
    private Integer displayRowCount;
    private Integer currentPage	;
    private Integer startPage	;

    private List<Long> outsvrmsgSeqList;
    // 공통코드 언어셋
    private String langSet		;
    //전문키 매핑
    private String paramKey		;
    private String cdNm		;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
