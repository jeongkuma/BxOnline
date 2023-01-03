package kr.co.scp.common.iotInsert.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceDTO;

public interface IotInsertServiceSVC {

	HashMap<String, Object> insertServiceSVC(TbIotInsertServiceDTO tbIotInsertServiceDTO) throws BizException;
	HashMap<String, Object> useChkService(TbIotInsertServiceDTO tbIotInsertServiceDTO) throws BizException;
}
