package kr.co.scp.ccp.login.ctl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.ccp.login.dto.LoginUserPersonalInfoDTO;
import kr.co.scp.ccp.login.svc.LoginSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotlogin")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class LoginCTL {
	@Autowired
	private LoginSVC loginSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Value("${management.isAdmin}")
	private boolean isAdmin;

	/**
	 * 로그인
	 *
	 * @author jbs
	 * @param request
	 * @param loginDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/processIotLogin", method = RequestMethod.POST)
	public ComResponseDto<?> processIotLogin(HttpServletRequest request, @RequestBody LoginDTO loginDTO) throws BizException {
		// 공통관리자가 서비스 서버 로그인시 토근값 중복사용 문제 해결용 코드 ( 임시방편 )
		if(!isAdmin&&loginDTO.getUsrLoginId().equals("SUPERADMIN")) {
			throw new BizException("userNotFound");
		}

		loginSVC.updateUsrOsDevice(request);
		HashMap<String, Object> loginMap = loginSVC.processIotLogin(loginDTO);
		return responseComUtil.setResponse200ok(loginMap);
	}

	/**
	 * 사용자 정보 조회 확인
	 *
	 * @author jbs
	 * @param request
	 * @param loginDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotUserInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUserInfo(HttpServletRequest request, @RequestBody LoginDTO loginDTO) throws BizException {
		HashMap<String, Object> loginMap = loginSVC.retrieveIotUserInfo(loginDTO);
		return responseComUtil.setResponse200ok(loginMap);
	}


	// 비밀 번호 변경 고객 아이디 / 사용자 아이디 / 이전비밀번호 / 이후비밀번호
	@RequestMapping(value = "/updateIotUsrPwd", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotUsrPwd(HttpServletRequest request, @RequestBody LoginDTO loginDTO) throws BizException{
		HashMap<String, Object> loginMap = loginSVC.updateIotUsrPwd(loginDTO);
		return responseComUtil.setResponse200ok(loginMap);
	}

	// 비밀 번호 다음에 변경
		@RequestMapping(value = "/updateIotUsrPwdNxt", method = RequestMethod.POST)
		public ComResponseDto<?> updateIotUsrPwdNxt(HttpServletRequest request, @RequestBody LoginDTO loginDTO) throws BizException{
			HashMap<String, Object> loginMap = loginSVC.updateIotUsrPwdNxt(loginDTO);
			return responseComUtil.setResponse200ok(loginMap);
		}

	// 사용자 개인정보 찾기
	@RequestMapping(value = "/retrieveIotUsrPersonalInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUsrPersonalInfo(HttpServletRequest request, @RequestBody LoginDTO loginDTO) throws BizException {
		LoginUserPersonalInfoDTO retrieveIotUsrPersonalInfo = loginSVC.retrieveIotUsrPersonalInfo(loginDTO);
		return responseComUtil.setResponse200ok(retrieveIotUsrPersonalInfo);
	}

}
