package kr.co.scp.common.iotInsert.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceRuleDTO;

public interface IotInsertServiceRuleSVC {
	
	HashMap<String, Object> insertServiceRuleSvc(TbIotInsertServiceRuleDTO tbIotInsertServiceRuleDTO) throws BizException;
}
