package kr.co.scp.common.tmpl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqListDTO;
import kr.co.scp.ccp.iotEntrDevStat.dto.TbIotEntrDevStatDTO;
import kr.co.scp.common.tmpl.dto.CopyJqGridDto;
import kr.co.scp.common.tmpl.dto.TbIotJqDataRequestDTO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import kr.co.scp.common.tmpl.dto.TbIotTmplHdrJqgridDTO;

@Mapper
public interface TbIotTmplHdrJqgridDAO {
	public List<TbIotTmplHdrJqgridDTO> retrieveTbIotTmplHdrJqgridList(TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto) throws BizException;

	public void deleteTbIotTmplHdrJqgrid(TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto) throws BizException;
	
	public void deleteTbIotTmplHdrJqgridAll(TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto) throws BizException;

	public void updateTbIotTmplHdrJqgrid(TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto) throws BizException;

	public void insertTbIotTmplHdrJqgrid(TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto) throws BizException;

	public List<TbIotJqDataResponseDTO> retrieveJqData(TbIotJqDataRequestDTO tbIotJqDataRequestDTO) throws BizException;

	public int duplicationCharSet(TbIotTmplHdrJqgridDTO chkDto) throws BizException;

	public List<TbIotJqDataResponseDTO> retrieveJqDataExcel(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) throws BizException;
	
	public List<TbIotJqDataResponseDTO> retrieveEntrJqDataExcel(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;

	public int duplicationCheckCopy(CopyJqGridDto copyJqGridDto) throws BizException;

	public List<TbIotTmplHdrJqgridDTO> retrieveOrgTbIotTmplHdrJqgridList(CopyJqGridDto copyJqGridDto) throws BizException;
}
