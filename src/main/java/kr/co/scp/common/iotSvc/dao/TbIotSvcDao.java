package kr.co.scp.common.iotSvc.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotSvc.dto.TbIotDevClsImgDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcCombinedDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcMDto;

@Mapper
public interface TbIotSvcDao {

	public List<TbIotSvcDto> retrieveIotSvc(TbIotSvcDto svcDto) throws BizException;

	public TbIotSvcDto retrieveIotSvcBySeq(TbIotSvcDto svcDto) throws BizException;

	public List<TbIotSvcMDto> retrieveDevClsList(TbIotSvcMDto svcMDto) throws BizException;

	public List<TbIotSvcMDto> retrieveSvcMapDevBySvcCd(TbIotSvcDto svcDto) throws BizException;

	public void deleteSvcMapDevCls(TbIotSvcMDto svcMDto) throws BizException;

	public void insertSvcMapDevCls(TbIotSvcDto svcDto) throws BizException;

	public void createIotSvc(TbIotSvcCombinedDto dto) throws BizException;
	
	public void createIotDevClsImg(TbIotDevClsImgDto tbIotDevClsImgDto) throws BizException;
	
	public void deleteIotDevClsImg(TbIotDevClsImgDto tbIotDevClsImgDto) throws BizException;
	
	public List<TbIotDevClsImgDto> retrieveIotDevClsImg(TbIotDevClsImgDto tbIotDevClsImgDto) throws BizException;
	
	public TbIotSvcDto retrieveIotSvcOnly(TbIotSvcDto svcDto) throws BizException;

	public List<TbIotSvcMDto> getServiceDevCls(TbIotSvcMDto tbIotSvcMDto) throws BizException;
	
	
	public List<HashMap<String, Object>>  retrieveIotCmCdForSvc(TbIotSvcMDto tbIotSvcMDto) throws BizException;
	
	public List<HashMap<String, Object>>  retrieveIotCmCdForDevDlsCd(TbIotSvcMDto tbIotSvcMDto)  throws BizException;
}
