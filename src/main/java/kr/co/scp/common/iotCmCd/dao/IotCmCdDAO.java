package kr.co.scp.common.iotCmCd.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdCDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdPDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdUDTO;

@Mapper
public interface IotCmCdDAO {

	public List<TbIotCmCdDTO> retrieveIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public Integer retrieveIotCmCdCount(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public TbIotCmCdUDTO retrieveIotCmCdById(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public TbIotCmCdUDTO retrieveIotCmCdByCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public String retrieveCdIdByCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdPDTO> retrieveIotParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotByParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public void createIotCmCd(TbIotCmCdCDTO tbIotCmCdCDTO) throws BizException;

	public void updateIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public void deleteIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public int retrieveDuplicatedCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public int retrieveDuplicatedCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotCmCdBySubString(TbIotCmCdDTO tbIotCmCdDTO);

	public List<TbIotCmCdOptionDTO> retrieveIotByParentCmCdRuntime(TbIotCmCdDTO tbIotCmCdDTO);

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotByParentCmCdOnly(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotByParentCmCdTwo(TbIotCmCdOptionDTO tbIotCmCdOptionDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotCmCdExcel(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	public String retrieveIotMaxOrder(TbIotCmCdDTO dto) throws BizException;

	public List<TbIotCmCdPDTO> retrieveIotParentCmCdOrderByCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

}
