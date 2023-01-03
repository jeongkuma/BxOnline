package kr.co.scp.ccp.iotEntrDevHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEntrDevHistReqListDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	// 조건 검색 변수
	private String custSeq;
	private String selectedCodition;
	private String inputText;
	private String devClsCdId;
	private String devMdlCd;
	private String devUuid;
	private String ctn;

	// 기간검색 변수
	private String searchStartDttm;
	private String searchEndDttm;

	//페이징 변수
	private Integer currentPage;
	private Integer displayRowCount;
	private int startPage;

	// jqGrid
	private String langSet;
	private String progId;
	private String tmpCdId;
	private String headerDevClsCdId;

	// 상위 조직 제외 검색 조건
	private String orgNm;

	// 식별명, 주소 필드 추가
	private String devUname;
	private String instAddr;


}
