package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetParamDTO;

public interface IotDevDetSetParamSVC {

	
	public void taskIotDevDetSetParam(TbIotDevDetSetParamDTO tbIotDevDetSetParamDTO) throws BizException ;
	
	public void insertTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;
	public void updateTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;
	public void deleteTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;
	
}

