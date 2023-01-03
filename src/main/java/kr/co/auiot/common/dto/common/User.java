package kr.co.auiot.common.dto.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class User {
	private String custId;
	private String svcCd;
	private String userId;
	private String userName;
//	private String email;
//	private String roles;
//	private String ip;
//	private String loginTime;

	private String custSeq;
	private String userSeq;

	private String roleCdId;
	private String roleCdNm;

	private String orgSeq;
	private String orgNm;
	
	private long time;

	public User() {
	}

	public User(String userId) {
		this.userId = userId;
//		this.roles = "OWNER";
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
