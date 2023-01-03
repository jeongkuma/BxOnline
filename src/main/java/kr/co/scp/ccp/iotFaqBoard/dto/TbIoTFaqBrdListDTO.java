package kr.co.scp.ccp.iotFaqBoard.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTFaqBrdListDTO {
	@JsonIgnore
	private String dymmy;
	private String faqSeq;
	private String categoryCdId;
	private String categoryCdNm;
	private String subject	;
	private String question	;
	private String answer	;
	private String interestYn;
	private String regDttm	;

}
