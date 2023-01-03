package kr.co.scp.ccp.iotCust.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbIotCustUDTO{
	@JsonIgnore
	private String dymmy;
    private String custSeq           ;
    private String langSet           ;
	private String custLoginId       ;
	private String custNm            ;
	private String svcCd             ;
	private String svcCdNm           ;
	private String custTypeCdId      ;
	private String CustTypeNm        ;
	private String custTelNo         ;
	private Integer loginStepCnt     ;
	private String custWlogoFile     ;
	private String custMlogoFile     ;
	private String custRegNo         ;
	private String useYn             ;
	private String usrNm             ;
	public String getDymmy() {
		return dymmy;
	}
	public void setDymmy(String dymmy) {
		this.dymmy = dymmy;
	}
	public String getCustSeq() {
		return custSeq;
	}
	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}
	public String getLangSet() {
		return langSet;
	}
	public void setLangSet(String langSet) {
		this.langSet = langSet;
	}
	public String getCustLoginId() {
		return custLoginId;
	}
	public void setCustLoginId(String custLoginId) {
		this.custLoginId = custLoginId;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSvcCd() {
		return svcCd;
	}
	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}
	public String getSvcCdNm() {
		return svcCdNm;
	}
	public void setSvcCdNm(String svcCdNm) {
		this.svcCdNm = svcCdNm;
	}
	public String getCustTypeCdId() {
		return custTypeCdId;
	}
	public void setCustTypeCdId(String custTypeCdId) {
		this.custTypeCdId = custTypeCdId;
	}
	public String getCustTypeNm() {
		return CustTypeNm;
	}
	public void setCustTypeNm(String custTypeNm) {
		CustTypeNm = custTypeNm;
	}
	public String getCustTelNo() {
		return custTelNo;
	}
	public void setCustTelNo(String custTelNo) {
		this.custTelNo = custTelNo;
	}
	public Integer getLoginStepCnt() {
		return loginStepCnt;
	}
	public void setLoginStepCnt(Integer loginStepCnt) {
		this.loginStepCnt = loginStepCnt;
	}
	public String getCustWlogoFile() {
		return custWlogoFile;
	}
	public void setCustWlogoFile(String custWlogoFile) {
		this.custWlogoFile = custWlogoFile;
	}
	public String getCustMlogoFile() {
		return custMlogoFile;
	}
	public void setCustMlogoFile(String custMlogoFile) {
		this.custMlogoFile = custMlogoFile;
	}
	public String getCustRegNo() {
		return custRegNo;
	}
	public void setCustRegNo(String custRegNo) {
		this.custRegNo = custRegNo;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}

}
