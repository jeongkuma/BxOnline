package kr.co.scp.common.iotInsert.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertDevMDTO extends IoTBaseDTO implements Cloneable{

	private String svcDevSeq;
	private String svcCd;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String statusCd;
	private String parentDevSeq;
	private String vendorNm;
	private String normalIconFile;
    private String abnormalIconFile;
	private String abnormalIconFile2;
	private String iconRegYn;
	private String devMdlVal;
	private String devSeq;
	private String regUsrId;
	private String modUsrId;



}
