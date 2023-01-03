package kr.co.abacus.common.api.dao.impl;

import kr.co.abacus.common.api.dao.MessageDAO;
import kr.co.abacus.common.api.dto.MessageDTO;
import kr.co.abacus.common.exception.BizException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	@Qualifier("mainSqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<Map<String, Object>> getMessageList(MessageDTO messageDTO) throws BizException {
		return sqlSessionTemplate.selectList("kr.co.abacus.common.api.message.getMessageList", messageDTO);
	}

}
