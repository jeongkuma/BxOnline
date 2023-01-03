package kr.co.scp.ccp.iotNotiBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIoTNotiBrdDetailDTO {
	@JsonIgnore
	private String dymmy;
    // 공지사항
	private String notiSeq		;
	private String subject		;
	private String content		;
	private String startDt		;
	private String endDt		;
	private String urgencyYn	;

	private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;

}
