package kr.co.scp.ccp.iotDev.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String devSeq;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devMdlVal;
	private String useYn;
	private String parentDevSeq;
	private String vendorNm;
	private String statusCd;
	private String regUsrId;
	private String modUsrId;
	private String normalIconFile;
	private String abnormalIconFile;
	private String abnormalIconFile2;
	public String orgDevSeq;
	public String devSvcCd;
	public String devSvcCdNm;
	public String iconRegYn;
	public String[] item;
	private String entrYn;
	private String langset;
	private String dailyColCnt;

private Integer displayRowCount;
	private Integer currentPage;

	private Integer startPage;
	private String isParent;
//	private String item;

	// 할당 제거
	private String svcCd;

}
