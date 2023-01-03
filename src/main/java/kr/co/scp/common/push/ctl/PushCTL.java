package kr.co.scp.common.push.ctl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import kr.co.scp.ccp.iotTopic.svc.IotTopicSVC;
import kr.co.scp.common.push.comp.PushScheduleData;
import kr.co.scp.common.push.dto.TopicDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/push")
public class PushCTL {

	@Autowired
	private IotTopicSVC topicSVC;

	@Autowired
	private PushScheduleData pushSchdData;

	@Autowired
	private ResponseComUtil responseComUtil;

	private ObjectMapper mapper;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException {
		this.mapper = new ObjectMapper();
		this.mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
	}

	/**
	 * topic 발급요청을 처리.
	 * 
	 * topic+전달인자로 hashed topic 을 만든 뒤 스케쥴러에 등록하고,
	 * hashed topic id 를 반환
	 * 
	 * @param request
	 * @param topicIssueDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/generateTopic", method = RequestMethod.POST)
	public ComResponseDto<?> generateTopic(HttpServletRequest request) throws BizException {
		log.debug("====== generateTopic");
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TopicDTO topicIssueDto = JsonUtils.fromJson(comInfoDto.getBodyString(), TopicDTO.class);	
		log.debug("====== " + comInfoDto.toString());

		String topicId = topicIssueDto.getTopicId();
		List<TbIotUsrDashConDTO> svcParam = topicIssueDto.getSvcParam();

		// 기준(base) topic 항목을 못 찾으면 에러.
		TbIotTopicDTO basePushItem = pushSchdData.findTopicItem(topicId);
		if (basePushItem == null) {
			throw new BizException("PushItemError");
		}

		// 스케쥴러 동작환경에서는 개개인의 유저 정보가 없기때문에 개인화 정보 세팅 시, 해당 정보를 기본적으로 추가한다.
		String custSeq = AuthUtils.getUser().getCustSeq();
		String orgSeq = AuthUtils.getUser().getOrgSeq();

		svcParam.add(new TbIotUsrDashConDTO("defCustSeq", custSeq));
		svcParam.add(new TbIotUsrDashConDTO("defOrgSeq", orgSeq));

		// hashed topic 주소 생성
		String hashedTopicId;
		try {
			String jsonstr = this.mapper.writeValueAsString(svcParam);
			hashedTopicId = topicId + "_" + DigestUtils.sha256Hex(jsonstr);
		} catch (JsonProcessingException e) {
			throw new BizException(e, "ObjectMapperError");
		}

		// push 스케쥴러에 등록.
		TbIotTopicDTO newPushItem = new TbIotTopicDTO(topicId, hashedTopicId, basePushItem.getDayTime(), basePushItem.getBeanCd(), svcParam, "T");
		pushSchdData.addPushItem(newPushItem);

		return responseComUtil.setResponse200ok(newPushItem);

	}

	/**
	 * 개인화 정보만 저장.
	 * 
	 * @param request
	 * @param topicIssueDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/saveTopicCond", method = RequestMethod.POST)
	public ComResponseDto<?> saveTopicCond(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TopicDTO topicIssueDto = JsonUtils.fromJson(comInfoDto.getBodyString(), TopicDTO.class);	

		String topicId = topicIssueDto.getTopicId();
		List<TbIotUsrDashConDTO> svcParam = topicIssueDto.getSvcParam();

		// 기준(base) topic 항목을 못 찾으면 에러.
		TbIotTopicDTO basePushItem = pushSchdData.findTopicItem(topicId);
		if (basePushItem == null) {
			throw new BizException("PushItemError");
		}

		// 개인화정보 저장
		topicSVC.saveIotUsrDashConList(basePushItem, svcParam);

		return responseComUtil.setResponse200ok("");
	}
}
