package kr.co.scp.ccp.iotDev.ctl;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrParamSVC;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevAttrParamCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotDevAttrParamSVC iotDevAttrParamSVC;



	/**
	 * 장비 속성 처리(등록/수정/삭제)
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/taskIotDevAttrParam", method = RequestMethod.POST)
	public ComResponseDto<?> taskIotDevAttrParam(HttpServletRequest request, @RequestBody TbIotDevAttrParamDTO tbIotDevAttrParamDTO) throws BizException {

//			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			iotDevAttrParamSVC.taskIotDevAttrParam(tbIotDevAttrParamDTO);

			return responseComUtil.setResponse200ok();

	}
}
