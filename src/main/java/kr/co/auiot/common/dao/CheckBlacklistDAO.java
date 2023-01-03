package kr.co.auiot.common.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;

@Mapper
public interface CheckBlacklistDAO {
	Integer checkBlackToken(String blackToken) throws BizException;
}
