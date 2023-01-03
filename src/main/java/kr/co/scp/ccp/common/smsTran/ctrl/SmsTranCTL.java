package kr.co.scp.ccp.common.smsTran.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.smsTran.dto.TbIotSmsTranDTO;
import kr.co.scp.ccp.common.smsTran.svc.SmsTranSVC;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/online/sms")
public class SmsTranCTL {

	@Autowired
	private SmsTranSVC smsTranSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * SMS 전송을 위해 Agent 전송 테이블(TB_IOT_SMS_TRAN)에 insert
	 * @param HttpServletRequest
	 * @param TbIotSmsTranDTO
	 * @return ComResponseDto
	 * @throws BizException
	 */
	@RequestMapping(value = "/tran", method = RequestMethod.POST)
	public ComResponseDto<?> smsTran(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TbIotSmsTranDTO tbIotSmsTranDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIotSmsTranDTO.class);	
		smsTranSVC.insertTbIotSmsTran(tbIotSmsTranDTO, false);
		return responseComUtil.setResponse200ok();
	}

}
