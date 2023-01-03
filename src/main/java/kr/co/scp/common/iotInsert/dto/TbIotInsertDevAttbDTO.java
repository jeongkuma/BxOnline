package kr.co.scp.common.iotInsert.dto;


import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertDevAttbDTO extends IoTBaseDTO implements Cloneable{

	private String svcDevAttbSeq;
	private String svcDevSeq;
	private String svcCd;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devAttbCdId;
	private String devAttbCdNm;
	private String devAttbType;
	private String inputType;
    private String statusCd;
	private String paramKey;
	private String colNmCd;
	private String conNmCd;
	private String staAvgNmCd;
	private String staSumNmCd;
    private String detNmCd;
	private String mapYn;
	private String unit;
	private String orderNo;
	private String devSeq;
    private String regUsrId;



}
