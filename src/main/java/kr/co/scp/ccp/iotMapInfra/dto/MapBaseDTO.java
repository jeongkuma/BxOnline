package kr.co.scp.ccp.iotMapInfra.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapBaseDTO {
	@JsonIgnore
	private String dymmy;
	private String api;
	private Map<String, String> headers;
	private Map<String, Object> body;

}
