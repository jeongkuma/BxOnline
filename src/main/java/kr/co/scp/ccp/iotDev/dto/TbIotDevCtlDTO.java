package kr.co.scp.ccp.iotDev.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIotDevCtlDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String  devMdlNm;         // 장비 모델명
	private String  devMdlCd;         // 장비 모델코드
	private String  vendorNm;      // 제조사명
	private String  devClsCd;      // 장비 유형 코드
	private String  devClsCdNm;
	private String  useYn;
	private String  devSeq;
	private String  orgDevSeq;
	private String  orgDevSetSeq;
	private String  devAttbSeq;
	public Integer  cdCnt;
    private String  cdId;
    private String  cdNm;
	private String  devDetSetSeq;
	private String  devAttbSetSeq;
	private String  regUsrId;
	private String devMdlVal;
	private String iconRegYn;

}// 장비 유형


