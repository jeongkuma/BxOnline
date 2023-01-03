package kr.co.scp.ccp.iotTerms.ctrl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotTerms.dto.TbIotTermsDTO;
import kr.co.scp.ccp.iotTerms.svc.TbIotTermsSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*",  exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotterms")
public class TbIotTermsCTL {

	@Autowired
	private TbIotTermsSVC termsSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 사용자 약관 목록 조회
	 * @param request
	 * @return ComResponseDto<List<TbIotTreeDTO>>
	 * @throws BizException
	 * @Author jbs
	 */
	@RequestMapping(value = "/retrieveIotTerms", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotSvc(HttpServletRequest request, @RequestBody TbIotTermsDTO termsDto) throws BizException {
		HashMap<String, Object> rnMap = termsSvc.retrieveTerms(termsDto);
		return responseComUtil.setResponse200ok(rnMap);
	}

	/**
	 * 사용자 약관 동의 이력 추가
	 * @param request
	 * @return ComResponseDto<List<TbIotTreeDTO>>
	 * @throws BizException
	 * @Author jbs
	 */
	@RequestMapping(value = "/createIotTermsAgr", method = RequestMethod.POST)
	public ComResponseDto<?> createIotTermsAgr(HttpServletRequest request, @RequestBody TbIotTermsDTO termsDto) throws BizException {
		termsSvc.createTermsAgr(termsDto);
		return responseComUtil.setResponse200ok();
	}

}
