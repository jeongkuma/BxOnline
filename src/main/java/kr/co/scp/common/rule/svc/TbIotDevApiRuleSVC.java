package kr.co.scp.common.rule.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleDTO;

public interface TbIotDevApiRuleSVC {

	public void insertTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

	public HashMap<String, Object> retrieveTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

	HashMap<String, Object> retrieveTbIotDevApiRuleInputList(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

}
