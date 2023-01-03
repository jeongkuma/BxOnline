package kr.co.scp.ccp.libraryBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import kr.co.auiot.common.util.AuthUtils;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTLibBrdDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
        // 자료실 테이블
		private String libSeq;
		private String subject;
		private String content;


		private Integer fileCnt;

		private String contentType;

		private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;
		private List<String> libSepList;

		//기간검색 변수
		private String searchStartDttm;
		private String searchEndDttm;
		private String selectedValue;
		private String inputValue;

		private Integer displayRowCount;
		private Integer currentPage;
		private Integer startPage;

		private String roleCdId;
		private String custSeq;


}
