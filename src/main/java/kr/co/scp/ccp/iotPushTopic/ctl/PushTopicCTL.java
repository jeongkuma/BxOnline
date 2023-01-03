package kr.co.scp.ccp.iotPushTopic.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.scp.ccp.iotPushTopic.dto.PushTopicPollingDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import kr.co.scp.ccp.iotTopic.svc.IotTopicSVC;
import kr.co.scp.common.push.comp.PushScheduleData;
import kr.co.scp.common.push.svc.PushTopicSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/iotPushTopic")
public class PushTopicCTL {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private IotTopicSVC topicSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private PushScheduleData pushData;

	@RequestMapping(value = "/polling", method = RequestMethod.POST)
	public ComResponseDto<?> polling(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		PushTopicPollingDTO param = JsonUtils.fromJson(comInfoDto.getBodyString(), PushTopicPollingDTO.class);	

		String topicId = (String) param.getTopicId();
		List<TbIotUsrDashConDTO> svcParam = (List<TbIotUsrDashConDTO>) param.getSvcParam();

		String custSeq = AuthUtils.getUser().getCustSeq();
		String orgSeq = AuthUtils.getUser().getOrgSeq();

		svcParam.add(new TbIotUsrDashConDTO("defCustSeq", custSeq));
		svcParam.add(new TbIotUsrDashConDTO("defOrgSeq", orgSeq));

		TbIotTopicDTO topic = new TbIotTopicDTO();
		topic.setTbIotUsrDashConList(svcParam);
		topic.setTopicId(param.getTopicId());

		TbIotTopicDTO item = pushData.findTopicItem(topicId);

		Object bean = context.getBean(item.getBeanCd());
		PushTopicSVC svc = (PushTopicSVC) bean;
		Object result = svc.run(topic);
		return responseComUtil.setResponse200ok(result);
	}

}
