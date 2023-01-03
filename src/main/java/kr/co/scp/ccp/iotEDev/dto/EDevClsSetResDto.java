package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 가입별 장비 등록 DTO
 * @author "pkjean"
 *
 */
@Setter @Getter @ToString
public class EDevClsSetResDto  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	//가입별 장비 마스터
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String ctn;
	private String usingNo;
	private String instNo;

	private int cSize;
	// 중복체크를 위한 변수
	private String msg;


}
