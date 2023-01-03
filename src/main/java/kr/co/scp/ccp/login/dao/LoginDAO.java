package kr.co.scp.ccp.login.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.ccp.login.dto.LoginUserPersonalInfoDTO;
import kr.co.scp.ccp.login.dto.LogoDTO;

@Mapper
public interface  LoginDAO {

	/**
	 * 로그인 테스트
	 * @param loginDTO
	 * @return
	 * @throws BizException
	 */
	LoginDTO selectTest(LoginDTO loginDTO) throws BizException;

	// 로그인 처리
	public LoginDTO processIotLogin(LoginDTO loginDTO) throws BizException;

	public LoginDTO processIotCheckUsrAuthNo(LoginDTO loginDTO) throws BizException;

	public LoginDTO retrieveIotUsrInfo(LoginDTO loginDTO) throws BizException;

	public LoginUserPersonalInfoDTO retrieveIotUsrPersonalInfo(LoginDTO loginDTO) throws BizException;

	public void updateLoginFailCnt(LoginDTO loginDTO) throws BizException;

	public void updateUsrLckYn(LoginDTO loginDTO) throws BizException;

	public int updateAuthNo(LoginDTO loginDTO) throws BizException;

	public void updateAuthSuccDttm(LoginDTO loginDTO) throws BizException;

	public void updateIotTermsAgreeYn(LoginDTO loginDTO) throws BizException;

	public void updateUsrLastLoginDttm(LoginDTO loginDTO) throws BizException;

	public void updateIotUsrPwd(LoginDTO loginDTO) throws BizException;

	public void updateIotUsrPwdNxt(LoginDTO loginDTO) throws BizException;

	public void updateIotUsrPwdHist(LoginDTO loginDTO) throws BizException;

	public List<LoginDTO> retrieveUsrPwdHist(LoginDTO loginDTO) throws BizException;
	
	public List<String> retrieveUsrPwdCur(LoginDTO loginDTO) throws BizException;

	public long retrieveCurrentTimeStamp() throws BizException;

	public String checkModDttm(LoginDTO loginDTO) throws BizException;

	public String checkTermsAgree(LoginDTO loginDTO) throws BizException;

	public String checkPwdVal(LoginDTO loginDTO) throws BizException;

	public String checkPwdNxt(LoginDTO loginDTO) throws BizException;

	public LogoDTO retrieveLogo(LogoDTO logoDTO) throws BizException;
}
