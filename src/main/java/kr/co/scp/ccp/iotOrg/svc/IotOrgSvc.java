package kr.co.scp.ccp.iotOrg.svc;

import java.util.HashMap;
import java.util.List;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgOptDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgRDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgUDTO;

public interface IotOrgSvc {

	public List<TbIotOrgOptDTO> retrieveIotOrgByUsr() throws BizException;

	public List<TbIotTreeDTO> retrieveIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public TbIotOrgRDTO retrieveIotOrgBySeq(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public String retrieveIotOrgNmChk(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public String retrieveIotOrgNm(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public void createIotOrg(TbIotOrgDTO tbIotOrgDTO) throws BizException;

	public void updateIotOrg(TbIotOrgUDTO tbIotOrgUDTO) throws BizException;

	public HashMap<String, Object> retrieveIotCustOrg() throws BizException;
}