package kr.co.abacus.common.api.dao.impl;

import kr.co.abacus.common.api.dao.RuleDAO;
import kr.co.abacus.common.api.dto.RuleDTO;
import kr.co.abacus.common.exception.BizException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RuleDAOImpl implements RuleDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<Map<String, Object>> getRules(RuleDTO ruleDTO) throws BizException {
		return sqlSessionTemplate.selectList("kr.co.abacus.common.api.rule.getRules", ruleDTO);
	}
	
	@Override
	public List<Map<String, Object>> getRulesSHcommand(RuleDTO ruleDTO) throws BizException {
		return sqlSessionTemplate.selectList("kr.co.abacus.common.api.rule.getRulesSHcommand", ruleDTO);
	}
	
//	@Override
//	public List<RuleDTO> getRuleList(RuleDTO ruleDTO) throws BizException {
//		return sqlSessionTemplate.selectList("kr.co.abacus.common.api.rule.getRuleList", ruleDTO);
//	}
}
