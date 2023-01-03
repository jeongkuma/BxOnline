package kr.co.scp.ccp.iotDev.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevDetSetParamSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevDetSetParamCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotDevDetSetParamSVC iotDevDetSetParamSVC;

	/**
	 * 장비 속성Set 처리(등록/수정/삭제)
	 * @param request
	 * @param tbIotDevDetSetParamDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/taskIotDevDetSetParam", method = RequestMethod.POST)
	public ComResponseDto<?> taskIotDevDetSetParam(HttpServletRequest request, @RequestBody TbIotDevDetSetParamDTO tbIotDevDetSetParamDTO) throws BizException {
	
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
			iotDevDetSetParamSVC.taskIotDevDetSetParam(tbIotDevDetSetParamDTO);
			
			return responseComUtil.setResponse200ok();
	}	
}