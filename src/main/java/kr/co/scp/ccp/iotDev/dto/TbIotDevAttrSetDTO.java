package kr.co.scp.ccp.iotDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevAttrSetDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String  devAttbSetCdId;
	private String  devAttbSetCdNm;
	private String  modUsrId;
    private String  statusCd;
	private String  devAttbSetSeq;
    private String  regUsrId;
    private String  devAttbSeq;
    private String  devAttbSetVal;
    private String  setParentCdId;


    private String mode;
    private String svcCd;




}// 장비 유형

