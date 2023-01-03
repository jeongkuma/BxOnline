package kr.co.scp.common.iotInsert.ctl;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceRuleDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertServiceRuleSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/com/iotInsertRule")
public class IotInsertServiceRuleCTL {
	
	@Autowired
	private IotInsertServiceRuleSVC iotInsertServiceRuleSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 서비스 rule 등록
	 * @param request
	 * @param tbIotInsertServiceRuleDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertServiceRuleSvc", method=RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertServiceRuleSvc(HttpServletRequest request, @RequestBody TbIotInsertServiceRuleDTO tbIotInsertServiceRuleDTO ) throws BizException{

		iotInsertServiceRuleSVC.insertServiceRuleSvc(tbIotInsertServiceRuleDTO);
		
		return responseComUtil.setResponse200ok();
	}
}
