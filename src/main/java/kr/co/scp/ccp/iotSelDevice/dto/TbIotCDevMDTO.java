package kr.co.scp.ccp.iotSelDevice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotCDevMDTO {
	@JsonIgnore
	private String dymmy;
	private String devClsCd;
	private String devClsCdNm;

	private int cntTotal;
	private int cntTrue;
	private int cntFalse;
	private int cntStop;

	private String norGroup;
	private String warGroup;
	private String stopGroup;
	private String mutGroup;
	private String mutWarGroup;

//	private String normalIconFile;
//	private String abnormalIconFile;
//	private String abnormalIconFile2;
//	private List<TbIotCDevAtbDTO> attbs;

}
