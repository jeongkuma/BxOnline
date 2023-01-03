package kr.co.scp.ccp.iotCust.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

// @Getter @Setter - 롬복 카멜케이싱 이슈로 직접 getter, setter 작성
public class TbIotCustCDTO {
	@JsonIgnore
	private String dymmy;
	private String custSeq          ;
	private String custLoginId       ;
	private String custNm            ;
	private String svcCd             ;
	private String custTypeCdId      ;
	private String custTelNo         ;
	private String custWlogoFile     ;
	private String custMlogoFile     ;
	private String custRegNo         ;
	private String uHldrCustNo       ;
	private String regUsrId          ;

	public String getSvcCd() {
		return svcCd;
	}
	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}
	public String getCustSeq() {
		return custSeq;
	}
	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
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
	public String getCustTypeCdId() {
		return custTypeCdId;
	}
	public void setCustTypeCdId(String custTypeCdId) {
		this.custTypeCdId = custTypeCdId;
	}
	public String getCustTelNo() {
		return custTelNo;
	}
	public void setCustTelNo(String custTelNo) {
		this.custTelNo = custTelNo;
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
	public String getuHldrCustNo() {
		return uHldrCustNo;
	}
	public void setuHldrCustNo(String uHldrCustNo) {
		this.uHldrCustNo = uHldrCustNo;
	}
	public String getRegUsrId() {
		return regUsrId;
	}
	public void setRegUsrId(String regUsrId) {
		this.regUsrId = regUsrId;
	}

}
