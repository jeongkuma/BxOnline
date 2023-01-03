package kr.co.scp.ccp.login.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class LoginDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String	custLoginId		;
	private String	custSeq			;
	private Integer	loginStepCnt	;
	private String  custTelNo		;
	private String	svcCd			;
	private String	usrSeq			;
	private String	usrLoginId		;
	private String	usrNm			;
	private String	usrPwd			;
	private String	usrPhoneNo		;
	private String	smsRcvNo		;
	private Integer	usrLckYn		;
	private Integer	loginFailCnt	;
	private Integer	smsAuthFailCnt	;
	private String	authCdId		;
	private String	authNo			;
	private String	authNoExpireDttm;
	private Integer	smsDelayMinute	;
	private String	pwdNxtDttm		;
	private String	lastLoginDttm	;
	private String	firstLoginYn	;
	private Integer	usrSlpYn		;
	private String	pwdModDttm		;
	private String	clientIp		;
	private String	termsAgreeYn	;
	private String	orgSeq			;
	private String	orgNm			;
	private String	roleCdId		;
	private String	roleCdNm		;

	// 비밀번호 변경 구분 필드
	private String	pwdChange		;

	// 공통코드 언어셋
	private String	langSet			;

	// DB 현재시간 조회
	private String	nowDttm			;

	// FUNC_ID 변경 판별 필드
	private boolean	secLogin		;
}
