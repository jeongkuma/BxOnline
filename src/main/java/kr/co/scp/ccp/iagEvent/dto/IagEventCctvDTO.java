package kr.co.scp.ccp.iagEvent.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class IagEventCctvDTO {
	@JsonProperty("subs_no")
	private String subsNo;
	@JsonProperty("group_code")
	private String groupCode;
	@JsonProperty("cust_no")
	private String custNo;
	@JsonProperty("subs_ctn")
	private String subsCtn;
	@JsonProperty("svc_cd")
	private String svcCd;
	@JsonProperty("prod_cd")
	private String prodCd;
	@JsonProperty("subs_type")
	private String subsType;
	@JsonProperty("cust_nm")
	private String custNm;
	@JsonProperty("cust_addr1")
	private String custAddr1;
	@JsonProperty("cust_full_addr")
	private String custFullAddr;
	@JsonProperty("bill_acnt_no")
	private String billAcntNo;
	@JsonProperty("subs_stus_code")
	private String subsStusCode;
	@JsonProperty("equip_manufacture")
	private String equipManufacture;
	@JsonProperty("equip_model")
	private String equipModel;
	@JsonProperty("equip_mac")
	private String equipMac;
	@JsonProperty("equip_sn")
	private String equipSn;
	@JsonProperty("device_type")
	private String deviceType;
}
