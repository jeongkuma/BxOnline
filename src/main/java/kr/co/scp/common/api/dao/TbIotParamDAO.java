package kr.co.scp.common.api.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.api.dto.TbIotParamDTO;

@Mapper
public interface TbIotParamDAO {
	public List<TbIotParamDTO> retrieveTbIotParamList(TbIotParamDTO tbIotParamDto) throws BizException;

	public void deleteTbIotParam(TbIotParamDTO tbIotParamDto) throws BizException;
	
	public void deleteTbIotParamAll(TbIotParamDTO tbIotParamDto) throws BizException;

	public void updateTbIotParam(TbIotParamDTO tbIotParamDto) throws BizException;

	public void insertTbIotParam(TbIotParamDTO tbIotParamDto) throws BizException;
	
	public List<HashMap<String, Object>> retrieveTbIotParamCd(TbIotParamDTO tbIotParamDto) throws BizException;
}
