package kr.co.scp.ccp.libraryBoard.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTLibBrdResListDTO extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
        // 자료실 테이블
		private Integer libSeq;
		private String subject;
		private String content;
		private Integer fileCnt;
		private String regUsrId;




}
