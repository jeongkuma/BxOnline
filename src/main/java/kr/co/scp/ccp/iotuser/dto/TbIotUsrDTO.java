package kr.co.scp.ccp.iotuser.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotUsrDTO{
	@JsonIgnore
	private String dymmy;
	private String usrSeq;
	private String custSeq;
	private String usrLoginId;
	private String usrPwd;
	private String usrNm;
	private String smsRcvNo;
	private String roleCdId ;
	private String roleCdNm ;
	private String useYn;
	private String usrSttsCdNm;
	private String firstLoginYn;
	private String termsAgreeYn;
	private String termsAgreeDttm;
	private String usrSlpYn;
	private String usrLckYn;
	private String smsUseYn;
	private String orgSetYn;
	private String orgSeq;
	private String orgNm;
	private String usrPhoneNo;
	private String usrEmail;
	private String regUsrId;
	private String regDttm;
	private String modUsrId;
	private String custUseYn;
	private String langSet;

	private String searchCon;
	private String searchVal;
	private String orderCol;
	private String orderStd;

	private String msg;

	private String[] usrSeqArr       ;
	private String arrType           ;
	private Integer displayRowCount  ;
	private Integer currentPage      ;
	private Integer startPage        ;
	private Integer endPage          ;
}
