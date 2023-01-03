package kr.co.scp.common.rule.ctrl;

import java.util.HashMap;

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
import kr.co.scp.common.rule.dto.TbIotDevApiRuleDTO;
import kr.co.scp.common.rule.svc.TbIotDevApiRuleSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/rule")
public class TbIotDevApiRuleCTL {

	@Autowired
	private TbIotDevApiRuleSVC devDevApiRuleSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/retrieveTbIotDevApiRule", method = RequestMethod.POST )
	public ComResponseDto<?> retrieveTbIotDevApiRule(HttpServletRequest request, @RequestBody TbIotDevApiRuleDTO tbIotDevApiRuleDTO)  throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> iotValRuleList = devDevApiRuleSVC.retrieveTbIotDevApiRule(tbIotDevApiRuleDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@RequestMapping(value = "/retrieveTbIotDevApiRuleInputList", method = RequestMethod.POST )
	public ComResponseDto<?> retrieveTbIotDevApiRuleInputList(HttpServletRequest request, @RequestBody TbIotDevApiRuleDTO tbIotDevApiRuleDTO)  throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> iotValRuleList = devDevApiRuleSVC.retrieveTbIotDevApiRuleInputList(tbIotDevApiRuleDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@RequestMapping(value = "/insertTbIotDevApiRule", method = RequestMethod.POST )
	public ComResponseDto<?> insertTbIoTValRule(HttpServletRequest request, @RequestBody TbIotDevApiRuleDTO tbIotDevApiRuleDTO )  throws BizException{

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		devDevApiRuleSVC.insertTbIotDevApiRule(tbIotDevApiRuleDTO);
		return responseComUtil.setResponse200ok();

	}
}
