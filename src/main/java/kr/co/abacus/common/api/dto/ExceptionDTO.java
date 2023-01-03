package kr.co.abacus.common.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ExceptionDTO extends CommonDTO {
	private String msg_seq;
	private String is_success;
	private String msg_key;
	private String msg_code;
	private String msg_type;
	private String msg_desc;
	private String msg_status;
	private String msg;	
}
