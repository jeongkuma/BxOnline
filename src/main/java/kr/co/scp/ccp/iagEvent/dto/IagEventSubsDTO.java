package kr.co.scp.ccp.iagEvent.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class IagEventSubsDTO {
	@JsonProperty("hldr_cust_no")
	private String hldrCustNo;
	@JsonProperty("entr_no")
	private String entrNo;
	@JsonProperty("aceno")
	private String aceNo;
	@JsonProperty("chng_bfr_prod_no")
	private String chngBfrProdNo;
	@JsonProperty("prod_no")
	private String prodNo;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
