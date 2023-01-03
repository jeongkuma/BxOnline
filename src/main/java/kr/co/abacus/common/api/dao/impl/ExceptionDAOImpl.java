package kr.co.abacus.common.api.dao.impl;

import kr.co.abacus.common.api.dao.ExceptionDAO;
import kr.co.abacus.common.api.dto.ExceptionDTO;
import kr.co.abacus.common.exception.BizException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExceptionDAOImpl implements ExceptionDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<Map<String, Object>> getExceptionList(ExceptionDTO exceptionDTO) throws BizException {
		return sqlSessionTemplate.selectList("kr.co.abacus.common.api.exception.getExceptionList", exceptionDTO);
	}
}
