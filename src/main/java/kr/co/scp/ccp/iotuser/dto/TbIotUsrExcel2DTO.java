package kr.co.scp.ccp.iotuser.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
public class TbIotUsrExcel2DTO{		
	@JsonIgnore
	private String dymmy;
	private String usrLoginId;
	private String usrNm;
	private String usrPhoneNo;
	private String orgNm;
	private String roleCdNm;	
	private String usrEmail;
	
	
	public String getUsrLoginId() {
		return usrLoginId;
	}
	public void setUsrLoginId(String usrLoginId) {
		this.usrLoginId = usrLoginId;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getUsrPhoneNo() {
		return usrPhoneNo;
	}
	public void setUsrPhoneNo(String usrPhoneNo) {
		this.usrPhoneNo = usrPhoneNo;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getRoleCdNm() {
		return roleCdNm;
	}
	public void setRoleCdNm(String roleCdNm) {
		this.roleCdNm = roleCdNm;
	}
	public String getUsrEmail() {
		return usrEmail;
	}
	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}
	
}