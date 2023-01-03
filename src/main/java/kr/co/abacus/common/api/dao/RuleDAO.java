package kr.co.abacus.common.api.dao;

import kr.co.abacus.common.api.dto.RuleDTO;
import kr.co.abacus.common.exception.BizException;

import java.util.List;
import java.util.Map;

public interface RuleDAO {
	public List<Map<String, Object>> getRules(RuleDTO ruleDTO) throws BizException;
	public List<Map<String, Object>> getRulesSHcommand(RuleDTO ruleDTO) throws BizException;
//	public List<RuleDTO> getRuleList(RuleDTO ruleDTO) throws BizException;
}
