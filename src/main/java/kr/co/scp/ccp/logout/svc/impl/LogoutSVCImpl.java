package kr.co.scp.ccp.logout.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.logout.dao.LogoutDAO;
import kr.co.scp.ccp.logout.dto.LogoutDTO;
import kr.co.scp.ccp.logout.svc.LogoutSVC;
import kr.co.auiot.common.util.OmsUtils;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

@Service
public class LogoutSVCImpl implements LogoutSVC {
	@Autowired
	private LogoutDAO logoutDAO;

	@Override
	public int processIotLogout(LogoutDTO logoutDTO) throws BizException {
		int result = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "loginDAO.updateUsrLckYn");
		try {
			result = logoutDAO.processIotLogout(logoutDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return result;
	}
}
