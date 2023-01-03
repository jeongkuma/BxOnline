package kr.co.scp.common.iotInsert.dto;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertServiceDTO extends IoTBaseDTO implements Cloneable{

	private String cdSeq;
	private String langSet;
	private String cdId;
	private String cdNm;
	private String useYn;
	private String parentCdId;
	private String firstCdId;
	private String cdOrder;
	private String paramKey;
	private String paramVal;
	private String cdDesc;
	private String lvl;

	private String svcSeq;
	private String svcCd;
	private String svcCdNm;
	private String devClsCd;
	private String svcLevel;
	private String svcOrder;
	private String upSvcSeq;

	private String devClsSeq;
//	private String devClsCd;
	private String iconCd;
	private String orgFile;
	private String serverFile;
	private String webUri;

	private String regUsrId;

	private List<TbIotInsertServiceDTO> cmCdList;
	private List<TbIotInsertServiceDTO> svcCdList;
	private List<List<TbIotInsertServiceDTO>> clsImgList;


	// 깊은 복사 함수
	public Object clone(){ Object obj = null; try{ obj = super.clone(); }catch(Exception e){} return obj; }

}
