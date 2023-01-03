package kr.co.scp.common.iotCmCd.svc;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdCDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdPDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdUDTO;

public interface IotCmCdSVC {

	public HashMap<String, Object> retrieveIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException ;

	public TbIotCmCdUDTO retrieveIotCmCdById(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public TbIotCmCdUDTO retrieveIotCmCdByCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdPDTO> retrieveIotParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotByParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotByParentCmCdOnly(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public void createIotCmCd(TbIotCmCdCDTO tbIotCmCdCDTO) throws BizException;

	public void updateIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public void deleteIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public int retrieveDuplicatedCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public int retrieveDuplicatedCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotCmCdBySubString(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotByParentCmCdRuntime(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotByParentCmCdTwo(TbIotCmCdOptionDTO tbIotCmCdOptionDTO) throws BizException;

	public String retrieveCdIdByCdNm(String cdNm) throws BizException;

	public XSSFWorkbook excelCreate(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public String retrieveIotMaxOrder(TbIotCmCdDTO dto) throws BizException;

	public List<TbIotCmCdPDTO> retrieveIotParentCmCdOrderByCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;


}

