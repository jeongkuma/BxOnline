package kr.co.scp.ccp.iotCust.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCustDTO{
	@JsonIgnore
	private String dymmy;
    private String custSeq           ;
	private String custLoginId       ;
	private String custNm            ;
	private String langSet           ;
	private String svcCd             ;
	private String svcCdNm           ;
	private String custTypeCdId      ;
	private String CustTypeNm        ;
	private String custTelNo         ;
	private Integer chargeSmsLimitCnt;
	private Integer loginStepCnt     ;
	private String custWlogoFile     ;
	private String custMlogoFile     ;
	private String custRegNo         ;
	private String uHldrCustNo       ;
	private String useYn             ;
	private String regUsrId          ;
	private String regDttm           ;
	private String modUsrId          ;
	private String modDttm           ;
	private String[] deleteSeq       ;

	private String msg               ;
	private String searchCon         ;
	private String searchVal         ;

	private Integer displayRowCount  ;
	private Integer currentPage      ;
	private Integer endPage          ;

}
