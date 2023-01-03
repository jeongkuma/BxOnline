package kr.co.scp.ccp.iagEvent.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.abacus.common.validation.ValidationComponent;
import kr.co.scp.ccp.iagEvent.dto.IagEventCctvDTO;
import kr.co.scp.ccp.iagEvent.dto.IagEventDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEventCctvSVC;
import kr.co.scp.ccp.iagEvent.svc.IagEventSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping(value = "/online/iag")
public class IagEventCTL {

	@Autowired
	private IagEventSVC iagEventSVC;

	@Autowired
	private IagEventCctvSVC iagEventCctvSVC;

	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private ValidationComponent validationComponent;

	/**
	 * IAG에서 수신된 이벤트 처리
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public ComResponseDto<?> iagEvent(HttpServletRequest request) throws BizException {
		ComInfoDto commonInfo = ReqContextComponent.getComInfoDto();
		String requestUri = commonInfo.getRequestUri();
		
		// HTTP_Provisioning - 03. 무선
		if (commonInfo.getBodyJson().get("subs") != null) { // '무선' 인지? 'CCTV' 인지? 구분을 subs 로 구분하였음. 
			commonInfo.setRequestUri("/online/iag/event/wireless");
			try {
				validationComponent.vaildate(commonInfo);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw (BizException) e;
			}
			commonInfo.setRequestUri(requestUri);
			commonInfo.setMid("P20001");
			commonInfo.setChannel("IAG");
			IagEventDTO iagEventDTO = JsonUtils.fromJson(commonInfo.getBodyString(), IagEventDTO.class);
			commonInfo.setFuncId(iagEventSVC.getFuncId(iagEventDTO.getEventCode()));
//			iagEventSVC.iagEvent(iagEventDTO, commonInfo);
			
		// HTTP_Provisioning - 05. CCTV
		} else {
			commonInfo.setRequestUri("/online/iag/event/cctv");
			try {
				validationComponent.vaildate(commonInfo);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw (BizException) e;
			}
			commonInfo.setRequestUri(requestUri);
			commonInfo.setMid("P20001");
			commonInfo.setChannel("IAG");
			IagEventCctvDTO iagEventCctvDTO = JsonUtils.fromJson(commonInfo.getBodyString(), IagEventCctvDTO.class);
			commonInfo.setFuncId(iagEventCctvSVC.getFuncId(iagEventCctvDTO.getSubsStusCode()));
//			iagEventCctvSVC.iagEvent(iagEventCctvDTO, commonInfo);
		}

		return responseComUtil.setResponse200ok();
	}
}
