package kr.co.scp.ccp.logout.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.logout.dto.LogoutDTO;

public interface LogoutSVC {
	int processIotLogout(LogoutDTO logoutDTO) throws BizException;
}
