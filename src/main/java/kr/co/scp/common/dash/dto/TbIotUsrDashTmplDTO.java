package kr.co.scp.common.dash.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.common.api.dto.TbIotParamDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotUsrDashTmplDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String usrDashTmplSeq;
	private String orgSeq;
	private String custSeq;
	private Integer dashLocNo;
	private String tmplSeq;
	private Integer coordXNo;
	private Integer coordYNo;
	private Integer width;
	private Integer height;
	private String apiSeq;
	private String apiUrl;
	private String reqMsg;
	private String rspMsg;
	private String devClsCd;
	private String devClsCdNm;
	private String colNmCd;
	private String initMsg;

	private String dashTmplTitle;
	private String dashTmplDesc;

	private String tmplCdId;
	private String tmplCdNm;
	private String tmplRuleImg;
	private String tmplGubun;
	private String usrTmplGubun;

	private List<TbIotParamDTO> tbIotParamDTOList;
	private List<TbIotUsrDashTmplDTO> tbItoUsrDashTmplDtoList;

	// 페이징을 위한 변수
	private Integer displayRowCount;
	private Integer currentPage;

	private String dayTime;
	private String topicId;

	private String userRole;
	private String svcCd;

}



