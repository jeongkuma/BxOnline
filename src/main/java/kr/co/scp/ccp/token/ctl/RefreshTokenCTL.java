package kr.co.scp.ccp.token.ctl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.token.dto.RefreshTokenDTO;
import kr.co.scp.ccp.token.svc.RefreshTokenSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotToken")
@CrossOrigin(value = { "*" }, exposedHeaders = { "Content-Disposition" })
public class RefreshTokenCTL {
	@Autowired
	private RefreshTokenSVC refreshTokenSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 토큰갱신
	 *
	 * @author will
	 * @param request
	 * @param refreshTokenDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	public ComResponseDto<?> refreshToken(HttpServletRequest request, @RequestBody RefreshTokenDTO refreshTokenDTO)
			throws BizException {
		HashMap<String, Object> refreshToken = refreshTokenSVC.processIotRefreshToken(refreshTokenDTO);
		return responseComUtil.setResponse200ok(refreshToken);
	}

}
