package kr.co.scp.ccp.iotCtrlHist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevMdlDTO {
	@JsonIgnore
	private String dymmy;
	/*// 장비유형
	private String devClsCdId;
	private String devClsCdNm;

	// 장비 모델
	private String devMdlCd;
	private String devMdlNm;

	//제어 종류
	private String col;
	private String colNm;

	//제어 상태
	private String prcCd;
	private String prcCdNm;

	//고객
	private String custSeq;*/

	private String codeId;
	private String codeNm;
}
