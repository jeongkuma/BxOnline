package kr.co.scp.ccp.libraryBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTLibBrdResDetailDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
        // 자료실 테이블
		private String libSeq;
		private String subject;
		private String content;
		private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;

		// 로그인한 사용자 아이디
		private String loginUserId;
		// 로그인한 사용자 권한
		private String roleCdId;

}
