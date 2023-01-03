package kr.co.scp.ccp.iotMgnt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IotMgntSDevDetSetDTO {

	@JsonIgnore
	private String dymmy;
	private String svcDevDetSetSeq;
	private String svcDevAttbSeq;
	private String svcCd;
	private String devClsCd;
	private String devDetSetCdId;
	private String devDetSetCdNm;
	private String detSetCond;
	private String statusCd;
	private String iconUrl;
	private String detSetDesc;
	private String regUsrId;
	private String regDttm;
	private String modUsrId;
	private String modDttm;
}
