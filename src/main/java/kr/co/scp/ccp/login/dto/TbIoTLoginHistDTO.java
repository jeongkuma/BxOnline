package kr.co.scp.ccp.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTLoginHistDTO {
	@JsonIgnore
	private String dymmy;
		private double loginHistSeq		;
		private String histRegDttm		;
		private String loginSuccYn		;
		private String clientIp			;
		private String usrLoginId		;
		private String usrSeq			;
		private String loginFailReason	;
		private String usrOs			;
		private String usrBrowser		;

		// 조회 조건 변수
		private String searchCondition	;
		private String selectedValue	;
		private String inputValue		;
		private Integer displayRowCount	;
		private Integer currentPage		;
		private Integer startPage		;

		// 단일 고객사 조회 변수
		private String custSeq			;
		private String roleCdId			;

		// 기간 조회 조건 변수
		private String searchStartDttm	;
		private String searchEndDttm	;

		// 공통코드 언어셋
		private String langSet			;

		// 단일 서비스 조회 변수
		private String svcCd			;

}
