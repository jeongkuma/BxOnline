package kr.co.scp.common.rule.ctrl;

import java.util.HashMap;
import java.util.List;

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
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleDTO;
import kr.co.scp.common.rule.svc.TbIotDevColRuleSVC;
import kr.co.scp.common.rule.svc.TbIotDevCtlRuleSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = { "*" }, exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/rule")
public class TbIotDevCtlRuleCTL {

	@Autowired
	private TbIotDevCtlRuleSVC tbIotDevCtlRuleSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/retrieveTbIotCtlValRuleInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotColValRuleInfo(HttpServletRequest request,
			@RequestBody TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> retrieveTbIotValRuleinfoList = tbIotDevCtlRuleSVC
				.retrieveTbIotCtlValRuleInfo(tbIotDevCtlRuleDTO);
		return responseComUtil.setResponse200ok(retrieveTbIotValRuleinfoList);
	}
	
	@RequestMapping(value = "/insertTbIoTDevCtlRule", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIoTDevColRule(HttpServletRequest request,
			@RequestBody TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotDevCtlRuleSVC.insertTbIoTDevCtlRule(tbIotDevCtlRuleDTO);
		return responseComUtil.setResponse200ok();
	}

	
}
