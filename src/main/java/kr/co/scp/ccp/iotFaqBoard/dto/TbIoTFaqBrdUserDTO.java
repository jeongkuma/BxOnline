package kr.co.scp.ccp.iotFaqBoard.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTFaqBrdUserDTO {
	@JsonIgnore
	private String dymmy;
	private String faqSeq;
	private String categoryCdId;
	private String categoryCdNm;
	private String question	;
	private String interestYn;
	private String useYn	;

}
