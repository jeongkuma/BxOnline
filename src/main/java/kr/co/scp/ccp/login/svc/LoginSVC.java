package kr.co.scp.ccp.login.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.ccp.login.dto.LoginUserPersonalInfoDTO;
import kr.co.scp.ccp.login.dto.LogoDTO;

public interface LoginSVC {

	// 로그인 인증  3-5-1
	public HashMap<String, Object> processIotLogin(LoginDTO loginDTO) throws BizException;

	// 사용자 정보 조회
	public HashMap<String, Object> retrieveIotUserInfo(LoginDTO loginDTO) throws BizException;

	// 유저 정보 세팅
	public String getUserToken(LoginDTO loginDTO) throws BizException;

	// 비밀 번호 변경   고객 아이디 / 사용자 아이디 / 이전비밀번호 / 이후비밀번호
	public HashMap<String, Object> updateIotUsrPwd(LoginDTO loginDTO) throws BizException;

	// 비밀번호 다음에 변경
	public HashMap<String, Object> updateIotUsrPwdNxt(LoginDTO loginDTO) throws BizException;

	// 회원 개인정보 조회
	public LoginUserPersonalInfoDTO retrieveIotUsrPersonalInfo(LoginDTO loginDTO) throws BizException;

	// 사용자 접근 OS, Browser 설정
	public void updateUsrOsDevice(HttpServletRequest request) throws BizException;

	// 고객사 로고 이미지 조회
	public HashMap<String, Object> retrieveIotLogo(LogoDTO logoDTO) throws BizException;
}
