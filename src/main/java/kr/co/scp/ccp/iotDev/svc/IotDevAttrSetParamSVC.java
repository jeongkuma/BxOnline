package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetParamDTO;

public interface IotDevAttrSetParamSVC {

	
	public void taskIotDevAttrSetParam(TbIotDevAttrSetParamDTO tbIotDevAttrSetParamDTO) throws BizException ;
	
	public void insertIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;
	public void updateIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;
	public void deleteIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;	
}

