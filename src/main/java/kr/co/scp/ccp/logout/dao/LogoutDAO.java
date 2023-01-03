package kr.co.scp.ccp.logout.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.logout.dto.LogoutDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogoutDAO {
	int processIotLogout(LogoutDTO logoutDTO) throws BizException;
}
