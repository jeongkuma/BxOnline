package kr.co.scp.ccp.iotNotiBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIoTNotiBrdDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
    // 공지사항
	private String notiSeq		;
	private String subject		;
	private String content		;
	private String startDt		;
	private String endDt		;
	private String urgencyYn	;
	private String useYn		;

	//조건 검색 변수
	private String allSearch	;
	private String searchStartDttm;
	private String searchEndDttm;
	private String isAdmin		;

	//단일 고객사 검색 변수
	private String roleCdId		;
	private String custSeq		;

	// 페이징 변수
	private Integer displayRowCount;
	private Integer currentPage	;
	private Integer startPage	;

	// 파일 관련 변수
	private Integer fileCnt		;
	private String contentType	;
	private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;
	private List<String> notiSeqList;


}
