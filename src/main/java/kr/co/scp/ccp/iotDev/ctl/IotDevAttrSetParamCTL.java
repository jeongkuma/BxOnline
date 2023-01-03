package kr.co.scp.ccp.iotDev.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSetParamSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevAttrSetParamCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotDevAttrSetParamSVC iotDevAttrSetParamSVC;

	
	
	/**
	 * 장비 속성Set 처리(등록/수정/삭제)
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/taskIotDevAttrSetParam", method = RequestMethod.POST)
	public ComResponseDto<?> taskIotDevAttrSetParam(HttpServletRequest request, @RequestBody TbIotDevAttrSetParamDTO tbIotDevAttrSetParamDTO) throws BizException {
	
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
			iotDevAttrSetParamSVC.taskIotDevAttrSetParam(tbIotDevAttrSetParamDTO);
			
			return responseComUtil.setResponse200ok();
	}	
}