package kr.co.auiot.common.spring.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

	private String token;

	private String custId;
	private String svcCd;
	private String userId;
	private String userName;
	private String custSeq;
	private String userSeq;
	private String roleCdId;
	private String roleCdNm;
	private String orgSeq;
	private String orgNm;

	public JWTAuthenticationToken(String token) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.token = token;
	}

//	public JWTAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String custId, String userId,
//			String userName, String ip, String loginTime, String token) {
//		super(authorities);
//		this.custId = custId;
//		this.userId = userId;
//		this.token = token;
//		this.userName = userName;
//		this.ip = ip;
//		this.loginTime = loginTime;
//		setAuthenticated(true);
//	}

	@Override
	public Object getCredentials() {
		return "N/A";
	}

	@Override
	public Object getPrincipal() {
		return userName;
	}

	public JWTAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String custId, String svcCd, String userId,
			String userName, String custSeq, String userSeq, String orgSeq, String orgNm, String roleCdId,
			String roleCdNm, String token) {
		super(authorities);
		this.custId = custId;
		this.svcCd = svcCd;
		this.userId = userId;
		this.userName = userName;
		this.custSeq = custSeq;
		this.userSeq = userSeq;
		this.orgSeq = orgSeq;
		this.orgNm = orgNm;
		this.roleCdId = roleCdId;
		this.roleCdNm = roleCdNm;
		this.token = token;
		setAuthenticated(true);
	}

}
