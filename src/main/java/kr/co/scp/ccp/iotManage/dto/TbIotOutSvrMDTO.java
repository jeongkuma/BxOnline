package kr.co.scp.ccp.iotManage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter @Getter
public class TbIotOutSvrMDTO extends IoTBaseDTO {
	@JsonIgnore
	private String dymmy;
	private Long outSvrSeq;
	private String custSeq;
	private String svcCd;
	private String serverNm;
	private String inoutCd;
	private String certKey;
	private String useYn;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
