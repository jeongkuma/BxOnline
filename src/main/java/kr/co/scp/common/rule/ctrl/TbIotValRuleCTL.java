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
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;
import kr.co.scp.common.rule.svc.TbIotValRuleSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/rule")
public class TbIotValRuleCTL {

	@Autowired
	private TbIotValRuleSVC tbIotValruleSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/retrieveTbIotValRuleInfoList", method = RequestMethod.POST )
	public ComResponseDto<?> retrieveTbIotValRuleInfoList(HttpServletRequest request, @RequestBody TbIotValRuleDTO tbIotValRuleDTO )  throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> iotValRuleList = tbIotValruleSvc.retrieveTbIotValRuleInfoList(tbIotValRuleDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@RequestMapping(value = "/insertTbIoTValRule", method = RequestMethod.POST )
	public ComResponseDto<?> insertTbIoTValRule(HttpServletRequest request, @RequestBody TbIotValRuleDTO tbIotValRuleDTO )  throws BizException{
		try {
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			tbIotValruleSvc.insertTbIoTValRule(tbIotValRuleDTO);
			return responseComUtil.setResponse200ok();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	@RequestMapping(value = "/updateTbIoTValRule", method = RequestMethod.POST )
	public ComResponseDto<?> updateTbIoTValRule(HttpServletRequest request, @RequestBody TbIotValRuleDTO tbIotValRuleDTO ) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotValruleSvc.insertTbIoTValRule(tbIotValRuleDTO);
		return responseComUtil.setResponse200ok();

	}
	@RequestMapping(value = "/deleteTbIoTValRule", method = RequestMethod.POST )
	public ComResponseDto<?> deleteTbIoTValRule(HttpServletRequest request, @RequestBody TbIotValRuleDTO tbIotValRuleDTO ) throws BizException  {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotValruleSvc.insertTbIoTValRule(tbIotValRuleDTO);
		return responseComUtil.setResponse200ok();

	}


}
