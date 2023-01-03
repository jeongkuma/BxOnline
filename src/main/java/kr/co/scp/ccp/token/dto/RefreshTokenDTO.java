package kr.co.scp.ccp.token.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RefreshTokenDTO {

	@JsonIgnore
	private String dymmy;
	private String refreshToken;

}
