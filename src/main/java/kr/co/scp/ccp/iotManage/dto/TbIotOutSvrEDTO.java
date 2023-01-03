package kr.co.scp.ccp.iotManage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter
@Getter
public class TbIotOutSvrEDTO extends IoTBaseDTO {
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
    private String tranCdNm;
    private String proxyYn;
    private String protocol;
    private String protocolNm;
    private String ip;
    private int port;
    private String callBackProtocol;
    private String callBackIp;
    private int callBackPort;
    private String certKey;
    private String useYn;
    private String regUsrId;

    //단일 고객사 조회 변수
    private String roleCdId		;

    // 페이징 변수
    private Integer displayRowCount;
    private Integer currentPage	;
    private Integer startPage	;

    private List<Long> outsvrmSeqList;
    private List<Long> outsvrinfoSeqList;

    // 공통코드 언어셋
    private String langSet		;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
