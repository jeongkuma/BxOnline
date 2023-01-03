package kr.co.abacus.common.api.dao;

import kr.co.abacus.common.api.dto.ValidationDTO;
import kr.co.abacus.common.exception.BizException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ValidationDAO {

	ValidationDTO getValidationDetail(ValidationDTO validationDTO) throws BizException;

	Integer insertValidation(ValidationDTO validationDTO) throws BizException;

	Integer updateValidation(ValidationDTO validationDTO) throws BizException;

	public Map<String, Object> getValidationDetailMap(ValidationDTO validationDTO) throws BizException;

	List<Map<String, Object>> getValidationRules(ValidationDTO validationDTO) throws BizException;
}
