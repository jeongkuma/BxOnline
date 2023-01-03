package kr.co.scp.ccp.iotEDev.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 장애sms설정 DTO
 * @author
 *
 */
@Setter @Getter @ToString
public class TbIotEdevDetAttbDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String devAttbCdNm;
	private String devDetSetCdNm;
	private String detSetCond;
	private String sendMsg;
	private String sendMsgTmpl;
	private String sendGubun;
	private String devDetSetCdId;
	private String detSetSeq;
	private String entrDevSeq;
	private String devClsCd;
	private String devMdlCd;
	private String devCurValSeq;
	private String recvTelNo;
	private String curValSeq;
	private String detApplyYn;
	private String devAttbCdId;
	private String langSet;

	// 상위 조직 제외 조건을 위한 필드 추가 20190905
	private String orgNm;
	private String custSeq;

}
