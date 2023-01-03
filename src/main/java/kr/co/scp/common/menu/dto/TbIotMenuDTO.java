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
public class TbIotMenuDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String menuSeq;
	private String menuCdId;
	private String menuCdNm;
	private String langSet;
	private String menuLevel;
	private String upMenuSeq;
	private String progSeq;
	private String progNm;
	private String progId;
	private Integer menuOrder;
	private String useYn;
	private String upMenuNm;
	private String svcCd;

	private Integer chkCnt;
	private String msg;
}
