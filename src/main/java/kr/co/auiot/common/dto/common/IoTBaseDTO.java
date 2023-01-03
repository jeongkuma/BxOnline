package kr.co.auiot.common.dto.common;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class IoTBaseDTO {
	private String regUserId;
	private String regUserNm;
	private String regDttm;
	private String modUserId;
	private String modDttm;
}
