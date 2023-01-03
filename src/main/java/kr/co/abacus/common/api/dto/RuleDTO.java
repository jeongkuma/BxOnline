package kr.co.abacus.common.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RuleDTO extends CommonDTO {
	private String dev_atb_id;
	private String cust_seq;
	private String dev_uuid;
	private String rule_col_type;
	private String rule_cmd_type;
	private String rule_dev_type;
	private String rule_uri;
	private String rule_kind;
}
