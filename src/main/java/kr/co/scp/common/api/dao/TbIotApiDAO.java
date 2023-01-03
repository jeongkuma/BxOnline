package kr.co.scp.common.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.api.dto.TbIotApiDTO;

@Mapper
public interface TbIotApiDAO {
	public List<TbIotApiDTO> retrieveTbIotApiList(TbIotApiDTO tbIotApiDto) throws BizException;

	public void deleteTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException;

	public void updateTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException;

	public Integer insertTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException;

	public int duplicationCheckApiNm(TbIotApiDTO tbIotApiDto) throws BizException;

	public int duplicationCheckApiUri(TbIotApiDTO tbIotApiDto) throws BizException;

	public int retrieveTbIotApiCount(TbIotApiDTO tbIotApiDto) throws BizException;

	public List<TbIotApiDTO> retrieveTbIotDashApiList(TbIotApiDTO tbIotApiDto) throws BizException;

	public TbIotApiDTO retrieveTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException;

}
