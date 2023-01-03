package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


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
public class TbIotEdevDetSmsUsrDTO  extends IoTBaseDTO{
	@JsonIgnore
	private String dymmy;
	private String usrSeq;
	private String usrLoginId;
	private String eDevDetSetSeq;
	private String lastId;
	private List delList;
	private String detSetSeq;
	private String regUsrId;

}
