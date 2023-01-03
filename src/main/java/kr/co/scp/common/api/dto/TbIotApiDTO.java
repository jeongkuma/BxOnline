package kr.co.scp.common.api.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotApiDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String apiSeq;
	private String apiNm;
	private String apiUri;
	private String apiDesc;
	private String apiFormat = "";
	private String gubun;
	private String serverGubun;
	private String serverNm;
	private String tmplGubun;
	private String langSet;

	private List<TbIotParamDTO> tbIotParamDTOList;

	private String mode;
	private Integer chkCnt;

	private String msg;

	// 삭제시 사용
	private List<String> apiSeqList;

	// 페이징을 위한 변수
	private Integer displayRowCount  ;
	private Integer currentPage      ;
}
