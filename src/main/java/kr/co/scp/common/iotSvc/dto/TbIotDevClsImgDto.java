package kr.co.scp.common.iotSvc.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevClsImgDto{
	@JsonIgnore
	private String dymmy;
	private String devClsSeq;
	private String devClsCd ;
	private String iconCd;
	private String serverFile;
	private String orgFile;
	private String webUri;
	private String multiIcon;
	private String regUsrId;
	private String modUsrId;

}
