package kr.co.scp.ccp.iotNotiBoard.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIoTNotiBrdListDTO {
	@JsonIgnore
	private String dymmy;
    // 공지사항
	private String notiSeq		;
	private String subject		;
	private String content		;
	private String regUsrId		;
	private String regDttm		;
	private String startDt		;
	private String endDt		;
	private String urgencyYn	;

}
