package kr.co.abacus.common.api.dao;

import kr.co.abacus.common.api.dto.MessageDTO;
import kr.co.abacus.common.exception.BizException;

import java.util.List;
import java.util.Map;

public interface MessageDAO {
	public List<Map<String, Object>> getMessageList(MessageDTO messageDTO) throws BizException;
}
