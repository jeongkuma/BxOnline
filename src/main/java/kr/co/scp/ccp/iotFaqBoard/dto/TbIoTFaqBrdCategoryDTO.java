package kr.co.scp.ccp.iotFaqBoard.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TbIoTFaqBrdCategoryDTO {
	@JsonIgnore
	private String dymmy;
	private String categoryCdId;
	private String categoryCdNm;
}
