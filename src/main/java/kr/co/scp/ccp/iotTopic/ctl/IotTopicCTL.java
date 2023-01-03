package kr.co.scp.ccp.iotTopic.ctl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotTopic.dto.SelectTopicInfoDTO;
import kr.co.scp.ccp.iotTopic.svc.IotTopicSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/iotTopic")
public class IotTopicCTL {

	@Autowired
	private IotTopicSVC iotTopicSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/getTopicList", method = RequestMethod.POST)
	public ComResponseDto<?> getTopicList(HttpServletRequest request) throws BizException {
		List<SelectTopicInfoDTO> topicList = iotTopicSVC.getTopicList();

		return responseComUtil.setResponse200ok(topicList);
	}

}
