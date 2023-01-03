package kr.co.scp.ccp.iotFaqBoard.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTFaqBrdDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String faqSeq;
	private String categoryCdId;
	private String categoryCdNm;
	private String subject	;
	private String question	;
	private String answer	;
	private String interestYn;
	private String useYn	;
	// 카테고리 부모 코드
	private String parentCdId	;

	//조건 검색 변수
	private String allSearch	;
	private String searchStartDttm;
	private String searchEndDttm;

	//단일 고객사 조회 변수
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
	private List<String> faqSeqList;

	// 공통코드 언어셋
	private String langSet		;


}
