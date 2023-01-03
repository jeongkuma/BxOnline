package kr.co.scp.ccp.iotDev.svc;


import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrParamDTO;


public interface IotDevAttrParamSVC {
	
	public void taskIotDevAttrParam(TbIotDevAttrParamDTO tbIotDevAttrParamDTO) throws BizException ;
	
	public void insertIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	public void updateIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	public void deleteIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	public void deleteTbIotDevAttbSetParam(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	public void deleteTbIotDevDetSetParam(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	public int retrieveIotAttbSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException;
	public int retrieveIotDetSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException;
}

