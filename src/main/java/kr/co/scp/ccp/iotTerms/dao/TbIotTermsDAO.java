package kr.co.scp.ccp.iotTerms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTerms.dto.TbIotTermsDTO;
import kr.co.scp.ccp.login.dto.LoginDTO;

@Mapper
public interface TbIotTermsDAO {

	public List<TbIotTermsDTO> checkTermsAgrYn(LoginDTO loginDto) throws BizException;

	public List<TbIotTermsDTO> retrieveTermsReq() throws BizException;

	public List<TbIotTermsDTO> retrieveTermsOpt() throws BizException;

	public void createTermsAgr(TbIotTermsDTO termsDto) throws BizException;
}
