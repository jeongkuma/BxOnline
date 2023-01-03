package kr.co.scp.ccp.logout.dto;

import lombok.*;

import kr.co.auiot.common.dto.common.IoTBaseDTO;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LogoutDTO extends IoTBaseDTO {
	private String blackToken;
}
