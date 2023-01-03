package kr.co.scp.ccp.iagEvent.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotIagEntrDevEvtHistDTO {
	private String ctn;
	private String eventCode;
	private String statusCd;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
