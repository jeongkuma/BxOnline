package kr.co.auiot.common.spring.security;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dao.CheckBlacklistDAO;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.dto.common.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
public class JWTAuthenticationProvider implements AuthenticationProvider {

	private final String secretKey;

	private final long DEF_TIME_DELAY = 1 * 24 * 60 * 60; // 1일 access_token
	
	private final long REFRESH_TIME_DELAY = 2 * 24 * 60 * 60; // 2일 refresh_token

	@Autowired
	private CheckBlacklistDAO checkBlacklistDAO;

	@SuppressWarnings("unused")
	private final UserRepository userRepository;

	private final long delayTime;

	public JWTAuthenticationProvider(String secretKey) {
		this.secretKey = secretKey;
		this.userRepository = null;
		this.delayTime = DEF_TIME_DELAY;
	}

	public JWTAuthenticationProvider(String secretKey, UserRepository userRepository) {
		this.secretKey = secretKey;
		this.userRepository = userRepository;
		this.delayTime = DEF_TIME_DELAY;
	}

	public JWTAuthenticationProvider(String secretKey, Long delayTime, UserRepository userRepository) {
		this.secretKey = secretKey;
		this.userRepository = userRepository;
		this.delayTime = delayTime;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JWTAuthenticationToken.class.isAssignableFrom(authentication)
				|| UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			return authentication instanceof JWTAuthenticationToken
					? getJWTAuthentication(((JWTAuthenticationToken) authentication).getToken())
					: getJWTAuthentication(getUser(authentication));
		} catch (RuntimeException | IOException e) {
			throw new InvalidAuthenticationException("Access denied", e);
		}
	}

	private Authentication getJWTAuthentication(User user) throws JsonProcessingException {
		String token = getToken(user);
		List<SimpleGrantedAuthority> grantedAuthorities = grantedAuthorities(user.getRoleCdId());
//		return new JWTAuthenticationToken(Collections.emptyList(), user.getUserid(), user.getUsername(), token);
		return new JWTAuthenticationToken(grantedAuthorities,
				user.getCustId(),
				user.getSvcCd(),
				user.getUserId(),
				user.getUserName(),
				user.getCustSeq(),
				user.getUserSeq(),
				user.getOrgSeq(),
				user.getOrgNm(),
				user.getRoleCdId(),
				user.getRoleCdNm(),
				token);
	}

	public String getToken(User user) {
		String token = Jwts.builder()
//				.setPayload(user.toString())
				.setSubject("UserInfo")
				.claim("custId", user.getCustId())
				.claim("svcCd", user.getSvcCd())
				.claim("userId", user.getUserId())
				.claim("userName", user.getUserName())
				.claim("custSeq", user.getCustSeq())
				.claim("userSeq", user.getUserSeq())
				.claim("orgSeq", user.getOrgSeq())
				.claim("orgNm", user.getOrgNm())
				.claim("roleCdId", user.getRoleCdId())
				.claim("roleCdNm", user.getRoleCdNm())
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setExpiration(new Date(System.currentTimeMillis() + (delayTime * 1000))).compact();
		return token;
	}

	private Authentication getJWTAuthentication(String token) {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
				
		if (checkBlacklistDAO.checkBlackToken(token) > 0) {
			
			log.debug("=== checkBlackToken :: InvalidAuthenticationException :: ");
			//throw new BizException("Access denied");
			throw new InvalidAuthenticationException("Access denied");
		}

//		Log.debug("#### token    : " + token);
//		Log.debug("#### custId   : " + claimsJws.getBody().get("custId", String.class));
//		Log.debug("#### userId   : " + claimsJws.getBody().get("userId", String.class));
//		Log.debug("#### userName : " + claimsJws.getBody().get("userName", String.class));
//		Log.debug("#### custSeq  : " + claimsJws.getBody().get("custSeq", String.class));
//		Log.debug("#### userSeq  : " + claimsJws.getBody().get("userSeq", String.class));
//		Log.debug("#### orgSeq   : " + claimsJws.getBody().get("orgSeq", String.class));
//		Log.debug("#### orgNm    : " + claimsJws.getBody().get("orgNm", String.class));
//		Log.debug("#### roleCdId : " + claimsJws.getBody().get("roleCdId", String.class));
//		Log.debug("#### roleCdNm : " + claimsJws.getBody().get("roleCdNm", String.class));

		List<SimpleGrantedAuthority> grantedAuthorities = grantedAuthorities(
				claimsJws.getBody().get("roleCdId", String.class));
		return new JWTAuthenticationToken(grantedAuthorities, claimsJws.getBody().get("custId", String.class),
				claimsJws.getBody().get("svcCd", String.class),
				claimsJws.getBody().get("userId", String.class),
				claimsJws.getBody().get("userName", String.class),
				claimsJws.getBody().get("custSeq", String.class),
				claimsJws.getBody().get("userSeq", String.class),
				claimsJws.getBody().get("orgSeq", String.class),
				claimsJws.getBody().get("orgNm", String.class),
				claimsJws.getBody().get("roleCdId", String.class),
				claimsJws.getBody().get("roleCdNm", String.class),
				token);
	}

	private List<SimpleGrantedAuthority> grantedAuthorities(String roles) {
		List<String> rolelist = new ArrayList<String>();
		rolelist.add(roles);
		return rolelist.stream().map(SimpleGrantedAuthority::new).collect(toList());
	}

	private User getUser(Authentication auth) throws IOException {
		// to do
		// Authentication에서 user정보 추출
		// DB에서 User 정보 조회
		// return userRepository.loadUser(getArgs of auth .....);
		return null;
	}
	
	public String getRefreshToken(User user) {
		String token = Jwts.builder()
//				.setPayload(user.toString())
				.setSubject("UserInfo")
				.claim("custId", user.getCustId())
				.claim("svcCd", user.getSvcCd())
				.claim("userId", user.getUserId())
				.claim("userName", user.getUserName())
				.claim("custSeq", user.getCustSeq())
				.claim("userSeq", user.getUserSeq())
				.claim("orgSeq", user.getOrgSeq())
				.claim("orgNm", user.getOrgNm())
				.claim("roleCdId", user.getRoleCdId())
				.claim("roleCdNm", user.getRoleCdNm())
				.claim("time", new Date(System.currentTimeMillis() + (REFRESH_TIME_DELAY * 1000)))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.setExpiration(new Date(System.currentTimeMillis() + (REFRESH_TIME_DELAY * 1000))).compact();
		return token;
	}

}
