package kr.co.scp.ccp.iagEvent.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class IagEventDTO {
	@JsonProperty("subs")
	IagEventSubsDTO iagEventSubsDTO;
	@JsonProperty("device")
	private List<IagEventDeviceDTO> iagEventDeviceDTOList;
	@JsonProperty("event_code")
	private String eventCode;
	@JsonProperty("iag_event_code")
	private String iagEventCode;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
