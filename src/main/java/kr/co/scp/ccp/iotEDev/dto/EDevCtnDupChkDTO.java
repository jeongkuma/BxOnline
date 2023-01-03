package kr.co.scp.ccp.iotEDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 고객장비등록
 * Ctn 중복체크
 * @author "pkjean"
 *
 */
@Setter @Getter @ToString
public class EDevCtnDupChkDTO{
	@JsonIgnore
	private String dymmy;

	private String cnt;

	private int chkSize;

	private String msg;


}
