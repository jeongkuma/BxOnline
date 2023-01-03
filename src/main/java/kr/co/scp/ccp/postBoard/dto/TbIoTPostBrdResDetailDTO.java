package kr.co.scp.ccp.postBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIoTPostBrdResDetailDTO extends IoTBaseDTO {
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
	private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;

	// 작성자
	private String regUserId;

	//로그인한 사용자
	private String loginUserId;
	//로그인한 사용자 권한
	private String roleCdId;

}
