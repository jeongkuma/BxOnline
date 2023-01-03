package kr.co.scp.common.dash.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.dash.dto.TbIotUsrDashTmplDTO;

@Mapper
public interface TbIotUsrDashTmplDAO {
	public List<TbIotUsrDashTmplDTO> retrieveTbIotUsrDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void deleteTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void updateTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void insertTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public List<TbIotUsrDashTmplDTO> retrieveTbIotDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void insertTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void updateTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void deleteTbIotUsrDashTmplAll(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public int retrieveTbIotDashTmplCnt(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;
}
