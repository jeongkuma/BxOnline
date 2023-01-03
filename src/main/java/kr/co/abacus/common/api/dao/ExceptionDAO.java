package kr.co.abacus.common.api.dao;

import kr.co.abacus.common.api.dto.ExceptionDTO;
import kr.co.abacus.common.exception.BizException;

import java.util.List;
import java.util.Map;

public interface ExceptionDAO {
	public List<Map<String, Object>> getExceptionList(ExceptionDTO exceptionDTO) throws BizException;
}
