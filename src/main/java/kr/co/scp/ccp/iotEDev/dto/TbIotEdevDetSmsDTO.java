package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 장애sms설정 DTO
 * @author  
 *
 */
@Setter @Getter @ToString
public class TbIotEdevDetSmsDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	//가입별 장비 마스터
	private String entrDevSeq;
	private String eventDttm;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String eventCode;
	private String iagEventCode;
	private String hldrCustNo;
	private String entrNo;
	private String aceno;
	private String chngBfrProdNo;
	private String prodNo;
	private String itemId;
	private String manfSerialNo;
	private String prevManfSerialNo;
	private String devEsn;
	private String custSeq;
	private String orgSeq;
	private String devUuid;
	private String ctn;
	private String devUname;
	private String machineNo;
	private String usingNo;
	private String instNo;
	private String bootDtm;
	//2019-04-17 Add
	private String devImgPath;
	private String devImgFileNm;
	private String devRegiDt;
	
	//장비설치위치
	private String eDevInsLocSeq;
	//private String instSiteSeq;
	private String instAddr;
	private String assistantAddr;
	private String instLat;
	private String instLon;
	
	//장비속성
	private Integer cDevAttbSeq;
	private Integer custDevAttbSeq;
	private String devAttbCdId;
	private String devAttbCdNm;
	private String inputType;
	
	//중복체크할 대상 컬럼
	private String dupleType;
	
	// 중복체크를 위한 변수
	private String msg;

	private String attbNm;
	private String detNm;
	private String devDetSetCdNm;
	private String sendMgs;
	 
	private String	custLoginId;
	private String	usrSeq;
	private String	usrLoginId;
	private String	usrNm;
	private String	usrPhoneNo;
	private String	smsRcvNo;
	private String	searchCon;
	private String	retrieveVal;
	private String	orgNm;
	private String	detSetCond;
	private String	sendMsg;
	private String	sendMsgTmpl;
	private String	devDetSetCdId;
	
	private String iconUrl;   
	private String smsText;   
	private String sendGubun;
	private List entrList;   
	private List rcvList;   
	private List attbList;   
	private String rcvObj;   
	private String regUsrId;
	
	private String eDevDetSetSeq;
	//private String entrDevAttbSeq;
	private String curValSeq;
	private String detSetSeq;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;
	private String charSet   ; 
	private String parentCdId   ; 
	private String paramKey  ; 
	private String paramVal  ;
	private String recvTelNo  ;
} 
