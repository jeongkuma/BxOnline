package kr.co.scp.ccp.token.svc.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.ccp.login.svc.LoginSVC;
import kr.co.scp.ccp.token.dto.RefreshTokenDTO;
import kr.co.scp.ccp.token.svc.RefreshTokenSVC;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.spring.SecurityWebConfig;
import kr.co.auiot.common.util.AuthUtils;

//@Slf4j
@Service
public class RefreshTokenSVCImpl implements RefreshTokenSVC {

	@SuppressWarnings("unused")
	@Autowired
	private Environment env = null;

	@Autowired
	private LoginSVC loginSVC;

	@Autowired
	SecurityWebConfig securityWebConfig;

	/**
	 * 토큰갱신 처리
	 */
	@Override
	public HashMap<String, Object> processIotRefreshToken(RefreshTokenDTO refreshTokenDTO) throws BizException {
		HashMap<String, Object> rnMap = new HashMap<String, Object>();

		// 1. 토큰갱신 time 체크
		if (AuthUtils.chkRefToken(refreshTokenDTO.getRefreshToken())) {

			User user = AuthUtils.getRefToken(refreshTokenDTO.getRefreshToken()); // refreshToken 객체 가져오기

			LoginDTO loginDTO = new LoginDTO();

			// ★★★★★ 사용자 DB 조회 ★★★★★
			// ★★★★★ 사용자 정책체크 ★★★★★

			loginDTO.setCustSeq(user.getCustSeq());
			loginDTO.setCustLoginId(user.getCustId());
			loginDTO.setSvcCd(user.getSvcCd());
			loginDTO.setUsrSeq(user.getUserSeq());
			loginDTO.setUsrLoginId(user.getUserId());
			loginDTO.setUsrNm(user.getUserName());
			loginDTO.setRoleCdId(user.getRoleCdId());
			loginDTO.setRoleCdNm(user.getRoleCdNm());
			loginDTO.setOrgSeq(user.getOrgSeq());
			loginDTO.setOrgNm(user.getOrgNm());

			// 토큰생성
			rnMap.put("auth", loginSVC.getUserToken(loginDTO));
			rnMap.put("refreshToken", getRefreshToken(loginDTO));

		} else {
			throw new BizException("RefreshTokenExpiration"); // 토큰만료 오류
		}

		return rnMap;
	}

	// User 정보 세팅, 갱신토큰 발급
	public String getRefreshToken(LoginDTO loginDTO) throws BizException {
		// Setter user
		User user = new User();
		user.setCustSeq(loginDTO.getCustSeq());
		user.setCustId(loginDTO.getCustLoginId());
		user.setSvcCd(loginDTO.getSvcCd());
		user.setUserSeq(loginDTO.getUsrSeq());
		user.setUserId(loginDTO.getUsrLoginId());
		user.setUserName(loginDTO.getUsrNm());
		user.setRoleCdId(loginDTO.getRoleCdId());
		user.setRoleCdNm(loginDTO.getRoleCdNm());
		user.setOrgSeq(loginDTO.getOrgSeq());
		user.setOrgNm(loginDTO.getOrgNm());
		String token = securityWebConfig.getRefreshToken(user);
		return token;
	}

}
