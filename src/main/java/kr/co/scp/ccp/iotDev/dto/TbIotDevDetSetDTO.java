package kr.co.scp.ccp.iotDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevDetSetDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String  devDetSetCdId;
	private String  devDetSetCdNm;
	private String  detSetCond;
	private String  iconUrl;
    private String  regUsrId;
    private String  modUsrId;
    private String  devDetSetSeq;
    private String  devAttbSeq;
    private String  detSetDesc;
    private String  statusCd;


    private String mode;
    private String langSet;
    private String svcCd;

}// 장비 유형

