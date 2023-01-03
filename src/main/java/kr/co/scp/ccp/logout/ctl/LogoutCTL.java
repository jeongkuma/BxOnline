package kr.co.scp.ccp.logout.ctl;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.scp.ccp.logout.dao.LogoutDAO;
import kr.co.scp.ccp.logout.dto.LogoutDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotlogout")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class LogoutCTL {
	@Autowired
	private LogoutDAO logoutDAO;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping("/processIotLogout")
	public ComResponseDto<?> processIotLogout(HttpServletRequest request) throws BizException {
		String token = request.getHeader(ComConstant.X_AUTH_TOKEN);
		LogoutDTO logoutDTO = new LogoutDTO();
		logoutDTO.setBlackToken(token);
		logoutDTO.setRegUserId(AuthUtils.getUser().getUserId());
		logoutDAO.processIotLogout(logoutDTO);

		HashMap<String, String> result = new HashMap<>();

		return responseComUtil.setResponse200ok(result);
	}
}
