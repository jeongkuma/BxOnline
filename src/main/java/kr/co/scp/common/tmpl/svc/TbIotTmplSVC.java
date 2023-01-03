package kr.co.scp.common.tmpl.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.common.tmpl.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface TbIotTmplSVC {
	public HashMap<String, Object> retrieveTbIotTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void insertTbIotTmpl(TbIotTmplDTO tbIotTmplDto, HttpServletRequest request) throws BizException;

	public void updateTbIotTmpl(HttpServletRequest request, TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void deleteTbIotTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;
	
	public List<TbIotJqDataResponseDTO> retrieveJqData(TbIotJqDataRequestDTO tbIotJqDataRequestDTO) throws BizException;
	
	public List<TbIotTmplDTO> retrieveTbIotTmplRule(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public List<TbIotTmplDTO> retrieveTbIotDashboardTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int duplicationCheck(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public HashMap<String, Object> retrieveTbIotTmplDetail(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public List<TbIotTmplHdrJqgridDTO> retrieveJqgrid(TbIotTmplHdrJqgridDTO tbJqgridDto) throws BizException;

	public void copyJqgrid(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int duplicationCharSet(TbIotTmplHdrJqgridDTO chkDto) throws BizException;

	public TbIoTBrdFileListDTO getFile(TbIoTBrdFileListDTO tbIoTBrdFileDTO) throws BizException;

	public HashMap<String, Object> retrieveTbIotJqgridTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void insertIotJqGridTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public void updateIotJqGridTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException;

	public int duplicationCheckCopy(CopyJqGridDto copyJqGridDto) throws BizException;

	public void copyJqTmplGrid(CopyJqGridDto copyJqGridDto) throws BizException;

	public String retrieveTbIotTmplCdId(TbIotTmplDTO tbIotTmplDto) throws BizException;

}
