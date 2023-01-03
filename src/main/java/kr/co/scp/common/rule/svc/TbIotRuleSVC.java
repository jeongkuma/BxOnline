package kr.co.scp.common.rule.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dto.TbIotRuleDTO;

import java.util.List;

public interface TbIotRuleSVC {


	public List<TbIotRuleDTO> retrieveTbIotRuleInfoList(TbIotRuleDTO tbIotRuleDTO) throws BizException;

	public void insertTbIotRule(TbIotRuleDTO tbIotRuleDTO) throws BizException;

	public void deleteTbIotRule(TbIotRuleDTO tbIotRuleDTO) throws BizException;



}
