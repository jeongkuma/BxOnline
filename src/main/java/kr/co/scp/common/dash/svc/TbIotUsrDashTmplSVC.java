package kr.co.scp.common.dash.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.dash.dto.TbIotUsrDashTmplDTO;

import java.util.HashMap;
import java.util.List;

public interface TbIotUsrDashTmplSVC {
	public List<TbIotUsrDashTmplDTO> retrieveTbIotUsrDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void deleteTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void insertTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public HashMap<String, Object> retrieveTbIotDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void insertTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void updateTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;

	public void saveTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException;
}
