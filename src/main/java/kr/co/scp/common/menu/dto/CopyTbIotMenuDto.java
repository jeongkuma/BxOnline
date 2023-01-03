package kr.co.scp.common.menu.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class CopyTbIotMenuDto extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String langSet;
	private String svcCd;
	private String orgLangSet;
	private String orgSvcCd;
	private String copyType;
	private String orgUpMenuSeq;
	
	private Integer chkCnt;
	private String msg;
}
