package kr.co.scp.common.prog.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotProgDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String progSeq;
	private String progNm;
	private String useYn;
	private String parentProgSeq;
	private String progId;
	private String progGubun;
	private String progUri;
	private String uiPath;

	// 삭제시 사용
	private List<String> progSeqList;

	// 페이징을 위한 변수
	private Integer displayRowCount  ;
	private Integer currentPage      ;
}
