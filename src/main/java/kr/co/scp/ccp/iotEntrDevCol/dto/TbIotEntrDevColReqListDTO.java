package kr.co.scp.ccp.iotEntrDevCol.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotEntrDevColReqListDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	// 조건 검색 변수
	private String custSeq;
	private String selectedCodition;
	private String inputText;
	private String devClsCdId;
	private String devMdlCd;

	// 기간검색 변수
	private String searchStartDttm;
	private String searchEndDttm;

	//페이징 변수
	private Integer currentPage;
	private Integer displayRowCount;
	private int startPage;

	// jqGrid
	private String charSet;
	private String progId;
	private String tmplCdId;
	private String headerDevClsCdId;

}
