package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


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
@Setter
@Getter
@ToString
public class TbIotEdevDetSetDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String eDevDetSetSeq;
	private String entrDevSeq;
	private String curValSeq;
	private String devDetSetCdId;
	private String devDetSetCdNm;
	private String detSetCond;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String detSetDesc;
	private String iconUrl;
	private String sendGubun;
	private String sendMsg;
	private String sendMsgTmpl;
	private String recvTelNo;
	private String regUsrId;
	private String detApplyYn;
	private Integer tempSeq;

}

