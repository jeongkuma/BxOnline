package kr.co.scp.common.tmpl.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.tmpl.dto.TbIotTmplDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbIotTmplDAO {
	public List<TbIotTmplDTO> retrieveTbIotTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void deleteTbIotTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void updateTbIotTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public Integer insertTbIotTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;
	
	public List<TbIotTmplDTO> retrieveTbIotTmplRule(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public List<TbIotTmplDTO> retrieveTbIotDashboardTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int retrieveTbIotTmplCount(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int duplicationCheck(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public TbIotTmplDTO retrieveTbIotTmplDetail(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void updateTmplFileNm(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int retrieveTbIotTmplJqgridCount(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public List<TbIotTmplDTO> retrieveTbIotTmplJqgridList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public String retrieveTbIotTmplCdId(TbIotTmplDTO tbIotTmplDto) throws BizException;
}
