package kr.co.scp.ccp.iotuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrDTO;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrRDTO;

@Mapper
public interface IotUsrDAO { 
	
	public List<TbIotUsrDTO> retrieveIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public String retrieveIotUsrCount(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public String retrieveDuplicationLgnId(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public void updateIotUsrInfo(TbIotUsrDTO tbIotUsrDTO) throws BizException;
	
	public void createIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public void updateIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException;	
	
	public TbIotUsrRDTO retrieveIotUsrBySeq(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public TbIotUsrDTO retrieveIotUsrBySeqInner(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public TbIotUsrDTO retrieveIotUsrById(TbIotUsrDTO tbIotUseDTO) throws BizException;
	
	public List<String> retrieveIotUsrAdminRole(TbIotUsrDTO tbIotUsrDTO) throws BizException;
	
	public String retrieveIotUstSeq() throws BizException;
	
	public List<TbIotUsrDTO> retrieveUsrListByCust(String custSeq) throws BizException;
	
	public List<TbIotUsrDTO> retrieveUsrPhoneDuplChk(TbIotUsrDTO tbIotUsrDTO) throws BizException;
	
	public String retrieveIotUsrCustInfo(String usrSeq) throws BizException;
}
