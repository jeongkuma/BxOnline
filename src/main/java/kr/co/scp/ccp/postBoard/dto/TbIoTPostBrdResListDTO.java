package kr.co.scp.ccp.postBoard.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIoTPostBrdResListDTO extends IoTBaseDTO {
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
	private Integer fileCnt;
	private String custNm;

	// 로그인한 사용자 권한
	private String roleCdId;
	// 로그인한 사용자
	private String loginId;


}
