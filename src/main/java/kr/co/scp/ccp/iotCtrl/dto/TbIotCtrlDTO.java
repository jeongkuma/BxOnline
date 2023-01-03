package kr.co.scp.ccp.iotCtrl.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotCtrlDTO extends IoTBaseDTO implements Cloneable{
	@JsonIgnore
	private String dymmy;
	private Integer ctrlHistSeq;
	private Integer ctrlRsvSeq;
	private String custSeq;
	private String devClsCd;
	private String devClsCdNm;
	private String resDate;
	private String ctn;
	private String entrNo;
	private String devAttrCdId;
	private String devAttrCdNm;
	private String pamKey;
	private String devVal;
	private String ctrlType;
	private String prcCd;
	private Integer retryCnt;
	private Integer sDevSeq;
	private String usingNo;
	private String regUsrId;
	private String modUsrId;
	private String itemList;

	private String devMdlNm;
	private String devMdlCd;
	private String instAddr;
	private String machineNo;
	private String colPeriod;
	private String sndPeriod;
	private String sndUnit;
	private String colUnit;
	private String svcCd;
	private String devUuid;
	private String devUname;
	private String entityId;
	private String command;
	private List<TbIotCtrlDTO> ctrList;
	private List<TbIotCtrlDTO> entrDevList;
	private String ctlDate;
	private String colDate;
	private String statusCd;
	private String statusNm;
	private String bootDtm;

    private String devSeq;
    private String entrDevSeq;
    private String[] entrDevSeqAr;
    private String[] devSeqAr;

    private String devAttbCdId;
    private String devAttbCdNm;
    private String setParentCdId;
    private String colCurDevVal;
    private String curDevVal;
    private String ctlType;
	private String ctlTypeNm;
    private String ctlSeq;
    private String devTime;
    private String inputType;
    private String devMdlVal;

    // 장비항목별 속성셋
    private String sDevAttbSeq;
    private String devAttbSetCdNm;
    private String devAttbSetVal;

	//기간검색 변수
	private String resDateStart;
	private String resDateEnd;

	private Integer displayRowCount;
	private Integer currentPage;
	private Integer startPage;

	private String langSet;

	private String orgNm;

	// 고객 요청사항 반영
	private String instNo;
	private String devBuildingNm;
	private String prcCdNm;


	// 깊은 복사 함수
	public Object clone(){ Object obj = null; try{ obj = super.clone(); }catch(Exception e){} return obj; }

}
