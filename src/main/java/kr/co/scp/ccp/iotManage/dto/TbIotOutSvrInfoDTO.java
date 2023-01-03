package kr.co.scp.ccp.iotManage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter @Getter
public class TbIotOutSvrInfoDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Long outSvrInfoSeq;
	private Long outSvrSeq;
	private String tranCd;
	private String proxyYn;
	private String protocol;
	private String ip;
	private int port;
	private String callBackProtocol;
	private String callBackIp;
	private int callBackPort;
	private String certKey;
	private String useYn;

	 // 페이징 변수
    private Integer displayRowCount;
    private Integer currentPage	;
    private Integer startPage	;

    // 공통코드 언어셋
    private String langSet		;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
