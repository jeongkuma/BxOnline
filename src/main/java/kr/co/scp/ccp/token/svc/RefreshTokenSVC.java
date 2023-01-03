package kr.co.scp.ccp.token.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.token.dto.RefreshTokenDTO;

public interface RefreshTokenSVC {
	HashMap<String, Object> processIotRefreshToken(RefreshTokenDTO refreshTokenDTO) throws BizException;
}
