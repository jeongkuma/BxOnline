package kr.co.scp.ccp.iagEvent.dto;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotEntrDevMapDTO {
	private BigInteger deviceId;
	private String devMdlCd;
	private String manfSerialNo;
	private String macAddr;
	private String devUuid;
	private String logId;
	private String status;
	private String brokerIp;
	private String brokerPort;
	private String eki;
	private String token;
	private String regUsrId;
	private String regDttm;
	private String modUsrId;
	private String modDttm;
}
