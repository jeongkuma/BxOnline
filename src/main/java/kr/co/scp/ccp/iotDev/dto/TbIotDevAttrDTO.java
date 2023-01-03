package kr.co.scp.ccp.iotDev.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevAttrDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String devAttbSeq;
	private String devSeq;
	private String devAttbCdId;
	private String devAttbCdNm;
	private String inputType;
	private String paramKey;
	private String minVal;
	private String maxVal;
	private String colNmCd;
	private String conNmCd;
	private String staAvgNmCd;
	private String staSumNmCd;
	private String detNmCd;
	private String mapYn;
	private String unit;
	private String orderNo;
	private String statusCd;
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
//	private String modDttm;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devAttbType;
	private String devMdlVal;

    private String mode;
    private String langSet;
    private String svcCd;



}// 장비 유형

