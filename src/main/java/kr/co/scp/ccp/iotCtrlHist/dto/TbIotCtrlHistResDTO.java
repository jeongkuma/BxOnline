package kr.co.scp.ccp.iotCtrlHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCtrlHistResDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	//제어이력 테이블
	private Integer ctrlHistSeq;
	private Integer ctrlRsvSeq;
	private Integer custSeq;

	private String custNm;

	private String devClsCdId;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String proDate;
	private String entrNo;
	private String ctn;
	private String entityId;
	private String devAttrCdId;
	private String devAttrCdNm;
	private String pamKey;
	private String devVal;
	private String col;
	private String prcCd;

	private String colNm;
	private String prcCdNm;

	private String retryCnt;

	//제어예약 예약일자
	private String requestDate;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

}
