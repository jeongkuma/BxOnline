package kr.co.abacus.common.api.ctrl;

import kr.co.abacus.common.api.dto.ValidationDTO;
import kr.co.abacus.common.api.svc.ValidationSVC;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController("abacus.ValidationCTL")
@RequestMapping(value = "/api/validation")
public class ValidationCTL {
	
//	@Autowired
//	private ValidationSVC validationSvc;
//
//	@Autowired
//	private ResponseComUtil responseComUtil;
//
//	/**
//	 * @Author 김경진
//	 * @param request
//	 * @param validationDTO
//	 * @return
//	 * @throws BizException
//	 */
//	@RequestMapping(value = "/getValidationList", method = RequestMethod.POST)
//	public ComResponseDto<?> getValidationDetail(HttpServletRequest request, @RequestBody ValidationDTO validationDTO) throws BizException {
//
//		List<ValidationDTO> resultDTO = validationSvc.getValidationRules(validationDTO);
//
//		return responseComUtil.setResponse200ok(resultDTO);
//	}
//
//
//	@RequestMapping(value = "/insertValidation", method = RequestMethod.POST)
//	public ComResponseDto<?> insertValidation(HttpServletRequest request, @RequestBody ValidationDTO validationDTO) throws BizException {
//
//		validationSvc.insertValidation(validationDTO);
//
//		return responseComUtil.setResponse200ok();
//	}
//
//	@RequestMapping(value = "/updateValidation", method = RequestMethod.POST)
//	public ComResponseDto<?> updateValidation(HttpServletRequest request, @RequestBody ValidationDTO validationDTO) throws BizException {
//
//		validationSvc.updateValidation(validationDTO);
//
//		return responseComUtil.setResponse200ok();
//	}
//
////	@RequestMapping(value = "/addValidationConfig", method = RequestMethod.POST)
////	public ComResponseDto<?> addValidationConfig(HttpServletRequest request, @RequestBody ValidationDTO validationDTO) throws BizException {
////
////		validationSvc.addValidationConfig(validationDTO);
////
////		return responseComUtil.setResponse200ok();
////	}
//
////	@RequestMapping(value = "/delValidationConfig", method = RequestMethod.POST)
////	public ComResponseDto<?> delValidationConfig(HttpServletRequest request, @RequestBody ValidationDTO validationDTO) throws BizException {
////
////		validationSvc.delValidationConfig(validationDTO);
////
////		return responseComUtil.setResponse200ok();
////	}
//
	
}
