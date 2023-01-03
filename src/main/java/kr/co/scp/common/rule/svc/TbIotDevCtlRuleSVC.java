package kr.co.scp.common.rule.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleDTO;

import java.util.HashMap;

public interface TbIotDevCtlRuleSVC {

	HashMap<String, Object> retrieveTbIotCtlValRuleInfo(TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO) throws BizException;

	void insertTbIoTDevCtlRule(TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO) throws BizException;

}
