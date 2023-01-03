package kr.co.scp.ccp.postBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIoTPostBrdDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	// 게시판 테이블
	private String postSeq;
	private String custSeq;
	private String custLoginId;
	private String custLoginNm;
	private String subject;
	private String content;
	private String regDate;
	private String custNm;

	private String contentType;
	private Integer fileCnt;

	private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;
	private List<TbIoTBrdFileListDTO> tbIoTPostBrdFileListDTOlist;
	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

	private List<String> postSepList;

	//기간검색 변수
	private String searchStartDttm;
	private String searchEndDttm;
	private String selectedValue;
	private String inputValue;


	//공지사항 등록한 유저 아이디
	private String regUserId;

	//로그인한 유저 아이디
	private String loginUserId;

	private String roleCdId;
}
