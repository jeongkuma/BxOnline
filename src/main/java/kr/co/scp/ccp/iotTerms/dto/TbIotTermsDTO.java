package kr.co.scp.ccp.iotTerms.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotTermsDTO {

	@JsonIgnore
	private String dymmy;

	private String usrSeq;
	private Integer iotTermsNo;
	private Integer termsAgrHistSeq;
	private String iotTermsKdCd;
	private String iotTermsNm;
	private String iotTermsOrder;
	private String iotTermsCntn;
	private String reqYn;
	private String useYn;
	private List<Integer> termsNoList;

	// 동의 여부 판별 필드
	private String checkYn;


	// 동의 이력 관련 필드
	private String termsAgrYn;
	private String termsAgrDttm;

	private String regUsrId;
	private String regDttm;
	private String modUsrId;
	private String modDttm;

	// 약관 이력 관련
	private String histCrteDttm;

	// 조회 필드
	private String custLoginId;
	private String usrLoginId;

	// 공통코드 언어셋
	private String langSet;
}
