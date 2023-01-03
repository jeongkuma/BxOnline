package kr.co.scp.ccp.iotCust.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCustCombineDTO{
	@JsonIgnore
	private String dymmy;
    private String custSeq           ;
	private String custLoginId       ;
	private String custNm            ;
	private String svcCd             ;
	private String custTypeCdId      ;
	private String CustTypeNm        ;
	private String custTelNo         ;
	private Integer chargeSmsLimitCnt;
	private String loginStepCnt     ;
	private String custWlogoFile     ;
	private String custMlogoFile     ;
	private String custRegNo         ;
	private String useYn             ;
	private String regUsrId          ;
	private String usrLoginId        ;
	private String usrPwd            ;
	private String usrNm             ;
	private String smsRcvNo          ;
	private String roleCdId          ;
	private String roleCdNm          ;
	private String usrPhoneNo        ;
	private String usrEmail          ;

}
