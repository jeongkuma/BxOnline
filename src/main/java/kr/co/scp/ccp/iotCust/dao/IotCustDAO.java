package kr.co.scp.ccp.iotCust.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCust.dto.TbIotCustCombineDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustSDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;

@Mapper
public interface IotCustDAO {

	public List<TbIotCustDTO> retrieveIotCust(TbIotCustDTO tbIotCustDTO)throws BizException;

//	public List<TbIotCustDTO> retrieveIotCustAll(TbIotCustDTO tbIotCustDTO)throws BizException;

	public String retrieveIotCustCount(TbIotCustDTO tbIotCustDTO)throws BizException;

	public TbIotCustUDTO retrieveIotCustBySeq(TbIotCustUDTO tbIotCustUDTO)throws BizException;

	public void createIoTCust(TbIotCustCombineDTO tbIotCustDTO) throws BizException;

	public void updateIotCust(TbIotCustDTO tbIotCustDTO) throws BizException;

	public void deleteIotCust(TbIotCustDTO tbIotCustDTO) throws BizException;

	public String retrieveDuplicatedCustId(TbIotCustDTO tbIotCustDTO) throws BizException;

	public String retrieveDuplicatedCustNm(TbIotCustDTO tbIotCustDTO) throws BizException;

	public List<TbIotCustSDTO> retrieveIotCustSelect(String svcCd)throws BizException;
}
