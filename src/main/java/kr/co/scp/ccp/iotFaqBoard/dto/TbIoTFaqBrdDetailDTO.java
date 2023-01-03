package kr.co.scp.ccp.iotFaqBoard.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTFaqBrdDetailDTO {
	@JsonIgnore
	private String dymmy;
	private String faqSeq;
	private String categoryCdId;
	private String categoryCdNm;
	private String subject	;
	private String question	;
	private String answer	;
	private String interestYn;

	private List<TbIoTBrdFileListDTO> tbIoTBrdFileListDTOlist;
}
