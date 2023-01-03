package kr.co.scp.ccp.iagEvent.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class IagEventDeviceDTO {
	@JsonProperty("item_id")
	private String itemId;
	@JsonProperty("manf_serial_no")
	private String manfSerialNo;
	@JsonProperty("prev_manf_serial_no")
	private String prevManfSerialNo;
	@JsonProperty("dev_esn")
	private String devEsn;
	@JsonProperty("iccid")
	private String iccid;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
