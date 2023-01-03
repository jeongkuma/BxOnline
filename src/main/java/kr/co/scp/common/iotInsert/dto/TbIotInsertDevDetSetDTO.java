package kr.co.scp.common.iotInsert.dto;


import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotInsertDevDetSetDTO extends IoTBaseDTO implements Cloneable{

	private String svcDevDetSetSeq;
	private String svcDevAttbSeq;
	private String devClsCd;
	private String devDetSetCdId;
	private String devDetSetCdNm;
	private String detSetCond;
	private String statusCd;
	private String iconUrl;
	private String detSetDesc;
	private String regUsrId;
//	private String regDttm;
	private String modUsrId;
	private String modDttm;
	private String svcCd;

}
