package kr.co.scp.common.cse.dto;


import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TbIotCseDTO extends IoTBaseDTO {
	private String svcCd;
	private String svcSvrCd;
	private String svcSvrNum;
	private String svcNm;

	private String internalYn;
	private String entityId;
	private String eki;
	private String token;
	private String mefSvrUrl;
	private String om2mSvrUrl;
	private String useYn;
//	private String regDttm;

	// 삭제시 사용
	private List<String> cseSeqList;

	// 페이징을 위한 변수
	private Integer displayRowCount  ;
	private Integer currentPage      ;
}
