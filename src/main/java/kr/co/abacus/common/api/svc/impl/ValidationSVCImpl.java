package kr.co.abacus.common.api.svc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.api.dao.ValidationDAO;
import kr.co.abacus.common.api.dto.ValidationDTO;
import kr.co.abacus.common.api.svc.ValidationSVC;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.validation.config.ValidationInfoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("abacus.svc.ValidationSVC")
public class ValidationSVCImpl implements ValidationSVC {
	
//	@Autowired
//	private ValidationDAO validationDAO;
//
//	@Autowired
//	private ObjectMapper mapper;
//
//	@Autowired
//	private ValidationInfoConfig validationConfig;
//
//	@Autowired
//	private Environment env;
//
////	@Override
////	public ValidationDTO getValidationDetail(ValidationDTO validationDTO) throws BizException {
////		return validationDAO.getValidationDetail(validationDTO);
////	}
//
//	@Override
//	public List<ValidationDTO> getValidationRules(ValidationDTO validationDTO) throws BizException {
//		List<Map<String, Object>> validationRules = validationDAO.getValidationRules(validationDTO);
//		for (Map<String, Object> map:validationRules) {
//
//		}
//		List<ValidationDTO> validationList = new ArrayList<ValidationDTO>();
//		return validationList;
//	}
//
//	@Override
//	public Integer insertValidation(ValidationDTO validationDTO) throws BizException {
//
//		return validationDAO.insertValidation(validationDTO);
//	}
//
//	@Override
//	public Integer updateValidation(ValidationDTO validationDTO) throws BizException {
//
//		return validationDAO.updateValidation(validationDTO);
//	}
//
////	@Override
////	public int addValidationConfig(ValidationDTO validationDTO) throws BizException {
////
////		int result = -1;
////
////		try {
////
////			ValidationDTO resultDTO = validationDAO.getValidationDetail(validationDTO);
////			if (null != resultDTO)
////			{
////				Map<String, Object> validMap = validationConfig.getValidationInfo();
////				validationConfig.setValidationInfo(validMap);
////
////				result = 1;
////			}
////
////		} catch (Exception e) {
////			log.error(e.getMessage());
////			throw new BizException(e, e.getMessage());
////		}
////
////		return result;
////	}
//
////	@Override
////	public int delValidationConfig(ValidationDTO validationDTO) throws BizException {
////
////		int result = -1;
////
////		try {
////
////			ValidationDTO resultDTO = validationDAO.getValidationDetail(validationDTO);
////			if (null != resultDTO)
////			{
////				Map<String, Object> validMap = validationConfig.getValidationInfo();
////
////				String ymlName = resultDTO.getHttpUri();
////				validMap.remove(ymlName);
////				validationConfig.setValidationInfo(validMap);
////
////				result = 1;
////			}
////
////		} catch (Exception e) {
////			log.error(e.getMessage());
////			throw new BizException(e, e.getMessage());
////		}
////
////		return result;
////	}
//
////	private void setValidationConfigServers(String jsonData) throws Exception {
////
////		String url = "http://localhost:8090/ccp/common/validation/setValidationConfig";
////
////		HttpUtils.urlConnectionJSON(url, jsonData);
////	}
//
////	private void setEtcValidationConfig(Map<String, Object> requestMap) throws Exception {
////
////		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
////		Map<String, Object> validMap = validationConfig.getValidationInfo();
////
////		String ymlName = comInfoDto.getRequestUri();
////		String contextPath = env.getProperty("server.servlet.context-path");
////
////		if (contextPath.length() > 0) {
////			ymlName = ymlName.substring(contextPath.length() + 1);
////		}
////
////		if (ymlName.indexOf(".") > -1) {
////			ymlName = ymlName.substring(0, ymlName.lastIndexOf("."));
////		}
////
////		ymlName = ymlName.replaceAll("/", ".");
////		if (comInfoDto.getVname()!=null && !comInfoDto.getVname().isEmpty())
////			ymlName = comInfoDto.getVname() + "." + ymlName;
////
////		validMap.put(ymlName, requestMap);
////		validationConfig.setValidationInfo(validMap);
////	}
}
