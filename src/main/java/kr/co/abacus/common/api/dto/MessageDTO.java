package kr.co.abacus.common.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MessageDTO extends CommonDTO {
	private String lang_seq;
	private String lang_set;
	private String lang_type;
	private String msg_key;
	private String msg_desc;
}
