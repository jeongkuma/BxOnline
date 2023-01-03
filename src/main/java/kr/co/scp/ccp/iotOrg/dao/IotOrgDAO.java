package kr.co.scp.ccp.iotOrg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgOptDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgRDTO;

@Mapper
public interface IotOrgDAO {
	
	public List<TbIotOrgOptDTO> retrieveIotOrgByUsr(TbIotOrgDTO tbIotOrgOptDto) throws BizException;
	
	public List<TbIotOrgDTO> retrieveIotOrg(String custSeq) throws BizException;
	
	public List<TbIotOrgDTO> retrieveOrgChildren(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public TbIotOrgRDTO retrieveIotOrgBySeq(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public List<TbIotOrgDTO> retrieveIotOrgByOrder(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public String retrieveIotOrgNmChk(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public String retrieveIotOrgNm(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public void createIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public void updateIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
	public void deleteIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public List<TbIotOrgOptDTO> retrieveIotCustOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;
	
}
