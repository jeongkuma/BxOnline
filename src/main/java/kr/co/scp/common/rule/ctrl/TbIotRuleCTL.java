package kr.co.scp.common.rule.ctrl;

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
import kr.co.scp.common.rule.dto.TbIotRuleDTO;
import kr.co.scp.common.rule.svc.TbIotRuleSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/rule")
public class TbIotRuleCTL {

	@Autowired
	private TbIotRuleSVC tbIotRuleSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value="/retrieveTbIotRuleInfoList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotRuleInfoList(HttpServletRequest request, @RequestBody TbIotRuleDTO tbIotRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		try {
			List<TbIotRuleDTO> retrieveTbIotRuleinfo = tbIotRuleSvc.retrieveTbIotRuleInfoList(tbIotRuleDTO);
			return responseComUtil.setResponse200ok(retrieveTbIotRuleinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value="/insertTbIotRule", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotRule(HttpServletRequest request, @RequestBody TbIotRuleDTO tbIotRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotRuleSvc.insertTbIotRule(tbIotRuleDTO);
		return responseComUtil.setResponse200ok();
	}



}
