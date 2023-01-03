package kr.co.scp.ccp.iagEvent.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotIagEntrDevDTO {
	private Integer entrDevSeq;
	private String  eventDttm;
	private String  eventCode;
	private String  iagEventCode;
	private IagEventSubsDTO   iagEventSubsDTO;
	private IagEventDeviceDTO iagEventDeviceDTO;
	private TbIotIagSvcDevDTO tbIotIagSvcDevDTO;
	private String  ctn;
	private String  devUuid;
	private String  baseSerialNo;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
