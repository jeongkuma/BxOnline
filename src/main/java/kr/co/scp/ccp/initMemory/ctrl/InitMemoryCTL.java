package kr.co.scp.ccp.initMemory.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.initMemory.svc.InitMemorySVC;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/online/init")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class InitMemoryCTL {

	@Autowired
	private InitMemorySVC initMemorySVC;

	
	@Autowired
	private ResponseComUtil responseComUtil;
	

	/**
	 * 초기화 이벤트 처리
	 * @param HttpServletRequest
	 * @return ComResponseDto
	 * @throws BizException
	 */
	@RequestMapping(value = "/memoryc", method = RequestMethod.POST)
	public ComResponseDto<?> initMemoryC(HttpServletRequest request) throws BizException {
		log.debug("=== memoryc start === ");
		ComRequestDto comRequestDto = ReqContextComponent.getComRequestDto();
		initMemorySVC.initMemoryC(comRequestDto);
		return responseComUtil.setResponse200ok();
	}
	@RequestMapping(value = "/memorya", method = RequestMethod.POST)
	public ComResponseDto<?> initMemoryA(HttpServletRequest request) throws BizException {
		log.debug("=== memorya start === ");
		ComRequestDto comRequestDto = ReqContextComponent.getComRequestDto();
		initMemorySVC.initMemoryA(comRequestDto);
		return responseComUtil.setResponse200ok();
	}
	
	@RequestMapping(value = "/ouifsync", method = RequestMethod.POST)
	public ComResponseDto<?> initOuifSync(HttpServletRequest request) throws BizException {
		log.debug("=== ouifsync start === ");
		ComRequestDto comRequestDto = ReqContextComponent.getComRequestDto();
		initMemorySVC.initOuifSync(comRequestDto);
		return responseComUtil.setResponse200ok();
	}}
