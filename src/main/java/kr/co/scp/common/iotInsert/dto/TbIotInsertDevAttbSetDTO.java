package kr.co.scp.common.iotInsert.dto;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertDevAttbSetDTO extends IoTBaseDTO implements Cloneable{

	private String svcDevAttbSetSeq;
	private String svcDevAttbSeq;
	private String svcCd;
	private String devAttbCdId;
	private String devAttbSetCdId;
	private String devAttbSetCdNm;
	private String statusCd;
	private String devAttbSetVal;
	private String devClsCd;
	private String regUsrId;
    private String setParentCdId;




}
