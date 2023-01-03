package kr.co.scp.ccp.login.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistDTO;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistListDTO;

@Mapper
public interface LoginHistDAO {
	// 로그인 처리 
	public void insertTbIotLoginHist(TbIoTLoginHistDTO tbIoTLoginiHistDTO) throws BizException;
	
	public List<TbIoTLoginHistListDTO> retrieveLoginHist(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException;
	
	public Integer retrieveLoginHistCount(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException;
	
	List<TbIoTLoginHistListDTO> retrieveIotLoginHistNotPage(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException;
}
