package kr.co.scp.common.rule.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;

public interface TbIotValRuleSVC {


	public HashMap<String, Object> retrieveTbIotValRuleInfoList(TbIotValRuleDTO tbIotValRuleDTO) throws BizException;

	public void insertTbIoTValRule(TbIotValRuleDTO tbIotValRuleDTO) throws BizException;


}
