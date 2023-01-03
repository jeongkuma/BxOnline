package kr.co.scp.ccp.common.smsTran.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotSmsTranDTO {
	private Integer trNum;
	private String  trSendDate;
	private String  trSendStat;
	private String  trMsgType;
	private String  trPhone;
	private String  trCallback;
	private String  trMsg;
	private String  trEtc1;  // 메시지유형 : 알림 / 로그인
	private String  trEtc2;  // 등급 : 일반 / 긴급
	private String  trEtc3;  // 고객순번 (CUST_SEQ)
	private String  trEtc4;  // 사용자순번 (USER_SEQ)
	private String  trEtc5;
	private String  trEtc6;
	private String  trId;
	private String  trRsltStat;
	private String  trRsltDate;
	private String  trModified;
	private String  trNet;
	private String  trRealSendDate;
	private String  trRouteId;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
