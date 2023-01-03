package kr.co.scp.common.tmpl.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotTmplDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String tmplSeq;
	private String tmplCdId;
	private String tmplCdNm;
	private String colCnt;
	private String progSeq;
	private String progNm;
	private String tmplGubun;
	private String tmplRuleImg;
	private String[] deleteSeq;
	private String initData;

	private List<TbIotTmplHdrJqgridDTO> tbIotTmplHdrJqgridList;

	private String mode;

	// 삭제시 사용
	private List<String> tmplSeqList;

	// 페이징을 위한 변수
	private Integer displayRowCount  ;
	private Integer currentPage      ;

	private String msg;
	private Integer chkCnt;
}
