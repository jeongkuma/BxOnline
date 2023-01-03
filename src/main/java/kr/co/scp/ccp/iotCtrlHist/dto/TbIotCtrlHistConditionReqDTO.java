package kr.co.scp.ccp.iotCtrlHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotCtrlHistConditionReqDTO {

	@JsonIgnore
	private String dymmy;
	private String custSeq;
	private String controlKind;
	private String controlStatus;

}
