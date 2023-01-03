package kr.co.scp.common.push.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.svc.IotTopicSVC;
import kr.co.scp.common.push.svc.PushTopicSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * push 서버 역할을 하는 컴포넌트.(push 스케쥴링)
 * 
 * @author ss
 * 
 */
@Slf4j
@Component
public class PushScheduler {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private PushScheduleData pushSchdData;

	@Autowired
	private IotTopicSVC topicSVC;

	@PostConstruct
	public void init() {
		//
	}

	/**
	 * spring container 에 스케쥴러로 등록되어, 1초마다 실행되며 다음 동작을 수행합니다.
	 * 
	 * 지정한 시간간격마다 push 항목 정보를 재 로드.
	 * 세팅된 push 항목 각각 에 대해서, 푸시주기가 도래하면 연결된 서비스를 실행하고 그 결과를 client 에 push.
	 * 
	 * @throws MessagingException
	 * @throws JsonProcessingException
	 */
	// @Scheduled(fixedDelay = 1000)
	public void tick() throws MessagingException, JsonProcessingException {

		// 지정한 시간간격마다 push 항목 정보를 새로 읽어옵니다.
		if (this.pushSchdData.isLoadTime()) {
			log.debug("====== " + "reload push item list");
			pushSchdData.updatePushList(topicSVC.retrieveIotTopicList());
		}

		// 세팅된 push 항목 각각 에 대해서, 푸시주기가 도래하면 연결된 서비스를 실행하고 그 결과를 client 에 push 합니다 
		for (TbIotTopicDTO topicItem : pushSchdData.iterate()) {
			try {
				String destination = topicItem.getTopicId();

				if (pushSchdData.isPushTime(destination, topicItem)) {

					PushTopicSVC svc = (PushTopicSVC) context.getBean(topicItem.getBeanCd());
					Object result = svc.run(topicItem);
					template.convertAndSend("/topic" + destination, mapper.writeValueAsString(result));

					pushSchdData.completed(destination);
				}

			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

	}

}
