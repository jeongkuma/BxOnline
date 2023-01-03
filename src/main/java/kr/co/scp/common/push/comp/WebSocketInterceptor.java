package kr.co.scp.common.push.comp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.spring.SecurityWebConfig;

/**
 * websocket connection, disconnection 등의 이벤트 발생 시 개입하여 처리하는 컴포넌트
 * 
 * @author ss
 *
 */
@Component
public class WebSocketInterceptor extends ChannelInterceptorAdapter {

	@Autowired
	private PushScheduleData pushSchdData;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		List<String> auths = accessor.getNativeHeader("X-AUTH");

		switch (accessor.getCommand()) {
			case CONNECT:
				if (auths == null || auths.size() <= 0) {
					throw new BizException("NoAuth");
				}
				String token = auths.get(0);

				try {
					SecurityWebConfig swc = new SecurityWebConfig();
					User user = swc.getUser(token);
					if (user == null || user.getUserSeq() == null) {
						throw new BizException("NoAuth");
					}
				} catch (Exception e) {
					throw new BizException("NoAuth");
				}
				break;
		}

		return super.preSend(message, channel);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		String sessionId = accessor.getSessionId();
		switch (accessor.getCommand()) {

			case DISCONNECT:
				// disconnect 이벤트가 발생하면 해당 session에 연결된 push(topic) 정보를 해제한다.
				pushSchdData.unsubscribeAll(sessionId);
				break;

			case SUBSCRIBE:
				// stompClient.subscribe 시 topic 에 sessionid 연결.
				pushSchdData.subscribe(parseTopicId(accessor.getDestination()), sessionId);
				break;

			case UNSUBSCRIBE:
				// stompClient.unsubscribe 시 topic 에서 sessionid 해제
				pushSchdData.unsubscribe(parseTopicId(accessor.getSubscriptionId()), sessionId);
				break;

			default:
				break;
		}

	}

	private String parseTopicId(String subscriptionId) {
		String topicId = "/" + subscriptionId.replaceAll("^/topic/*", "");
		return topicId;
	}

}
