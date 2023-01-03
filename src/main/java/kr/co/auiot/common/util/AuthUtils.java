package kr.co.auiot.common.util;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.spring.*;

public class AuthUtils {

	public static User getUser() {
		User user = new User();
		ComInfoDto comInfo = ReqContextComponent.getComInfoDto();
		if (StringUtils.isNotEmpty(comInfo.getAuth())) {
			SecurityWebConfig securityWebConfig = new SecurityWebConfig();
			user = securityWebConfig.getUser(comInfo.getAuth());
		}
		return user;
	}

	public static User getRefToken(String refToken) {
		User user = new User();
		if (StringUtils.isNotEmpty(refToken)) {
			SecurityWebConfig securityWebConfig = new SecurityWebConfig();
			user = securityWebConfig.getRefreshToken(refToken);
		}
		return user;
	}

	public static boolean chkRefToken(String refToken) {
		boolean chkFlag = false;
		User user = new User();
		if (StringUtils.isNotEmpty(refToken)) {
			SecurityWebConfig securityWebConfig = new SecurityWebConfig();
			user = securityWebConfig.getRefreshToken(refToken);
			long expTime = user.getTime();
			if (System.currentTimeMillis() <= expTime) { // 만료 시간 비교
				chkFlag = true;
			}
		}
		return chkFlag;
	}

}
