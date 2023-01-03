package kr.co.scp.common.iotSvc.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotSvcMDto {
	@JsonIgnore
	private String dymmy;
	public boolean checked ;
	public String svcSeq     ;
	public String svcCd      ;
	public String svcCdNm    ;
	public String devClsCd   ;
	public String devClsNm   ;
	public String parentCd   ;

	// 다중 등록을 위한 필드
	public List<TbIotSvcMDto> devClsList;

	public String charSet    ;
	public String langSet    ;
}
