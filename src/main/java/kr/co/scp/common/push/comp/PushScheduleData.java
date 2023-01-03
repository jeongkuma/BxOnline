package kr.co.scp.common.push.comp;

import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * push 상태 관리 객체.
 * 
 * 기준 push 목록 관리
 * push 대상 목록을 주기적으로 갱신하기 위해 갱신기준시점 관리
 * 개별 push항목의 푸싱주기를 확인을 위해 푸시기준시점 관리.
 * 사용자 구독상태 관리.
 * 
 * @author ss
 * 
 */
@Slf4j
@Getter
@Component
@Scope("singleton")
public class PushScheduleData {

	/**
	 * 기준(base) push(topic) 목록
	 */
	private List<TbIotTopicDTO> baseTopicList = null;

	/**
	 * 기준(base) topic 으로부터 확장(발급)된 hashed topic 목록.
	 */
	private List<TbIotTopicDTO> hashedTopicList = new ArrayList<TbIotTopicDTO>();

	/**
	 * 주기적으로 push 목록 갱신을 위해 timestamp 기록
	 */
	private long loadTimestamp = -1;

	/**
	 * 개별 push항목의 푸싱주기 관리를 위해 timestamp 기록
	 */
	private Map<String, Long> pushTimestamp = new HashMap<String, Long>();

//	@Value("${push.loadinterval}")
	private long loadinterval = 60;

	/**
	 * 다음과 같은 구조를 가지는 구독상태 관리.
	 * 
	 * topicId0: [sessionId1, sessionId2]
	 * topicId1: [sessionId1]
	 * topicId2: []
	 */
	private Map<String, Set<String>> subscription = new HashMap<String, Set<String>>();

	/**
	 * 
	 * push list 갱신 및 기준시점 세팅
	 * 
	 * @param pushList
	 */
	public void updatePushList(List<TbIotTopicDTO> pushList) {
		this.baseTopicList = pushList;
		this.loadTimestamp = System.currentTimeMillis();

		// map 준비
		HashMap<String, List<TbIotTopicDTO>> map = new HashMap<String, List<TbIotTopicDTO>>();
		for (TbIotTopicDTO item : hashedTopicList) {
			List<TbIotTopicDTO> list = (List<TbIotTopicDTO>) MapUtils.getObject(map, item.getBaseTopicId());
			if (list == null) {
				list = new ArrayList<TbIotTopicDTO>();
				map.put(item.getBaseTopicId(), list);
			}

			list.add(item);
		}

		// parent(base) topic 의 변경된 정보를 hashed topic 에 갱신.
		for (TbIotTopicDTO baseitem : this.baseTopicList) {
			List<TbIotTopicDTO> list = (List<TbIotTopicDTO>) MapUtils.getObject(map, baseitem.getTopicId());
			if (list != null) {
				for (TbIotTopicDTO item : list) {
					item.setBeanCd(baseitem.getBeanCd());
					item.setDayTime(baseitem.getDayTime());
				}
			}
		}
	}

	/**
	 * push 항목 추가.
	 * 
	 * @param topicItem
	 */
	public void addPushItem(TbIotTopicDTO topicItem) {
		for (TbIotTopicDTO item : this.hashedTopicList) {
			if (item.getTopicId().equals(topicItem.getTopicId())) {
				return;
			}
		}
		this.hashedTopicList.add(topicItem);
	}

	/**
	 * push 목록 갱신 주기 도래 여부
	 * 
	 * @return
	 */
	public boolean isLoadTime() {
		if (System.currentTimeMillis() - this.loadTimestamp > (1000 * loadinterval)) {
			return true;
		}
		return false;
	}

	/**
	 * topicId 에 대해 push 주기가 도래했는지 검사.
	 * 
	 * @param topicId
	 * @param interval
	 * @return
	 */
	public boolean isPushTime(String topicId, long interval) {

		long timestamp = MapUtils.getLongValue(this.pushTimestamp, topicId, 0);
		if (System.currentTimeMillis() - timestamp > interval) {
			return true;
		}
		return false;
	}

	/**
	 * topic 항목에 대해 push 주기가 도래했는지 검사.
	 * 
	 * @param topicId
	 * @param interval
	 * @return
	 */
	public boolean isPushTime(String topicId, TbIotTopicDTO topicItem) {
		String intervalKeyword = PushConstant.TMPL_GUBUN.INTERVAL.getValue();
		int interval = Integer.valueOf(topicItem.getDayTime());
		for (TbIotUsrDashConDTO con : topicItem.getTbIotUsrDashConList()) {
			// 개인화 정보 중 '_interval_' 이란 키워드드가 있으면 주기정보로 기준(base) topic 주기정보보다 우선하여 취급.
			if (intervalKeyword.equals(con.getTmplGubun())) {
				interval = Integer.valueOf(con.getTmplCondVlu());
			}
		}
		return this.isPushTime(topicId, interval);
	}

	/**
	 * push를 완료 한 뒤 다음 주기를 계산하기 위한 기준시점 세팅.
	 * 
	 * @param topicId
	 */
	public void completed(String topicId) {
		this.pushTimestamp.put(topicId, System.currentTimeMillis());
	}

	/**
	 * topicId 에 대한 sessionId 의 구독상태를 on 한다.
	 * 
	 * @param topicId
	 * @param sessionId
	 */
	public synchronized void subscribe(String topicId, String sessionId) {
		Set<String> sessionIdSet = (Set<String>) MapUtils.getObject(this.subscription, topicId);
		if (sessionIdSet == null) {
			sessionIdSet = new HashSet<String>();
		}
		sessionIdSet.add(sessionId);
		this.subscription.put(topicId, sessionIdSet);
	}

	/**
	 * topicId 에 대한 sessionId 의 구독상태를 off 한다.
	 * 
	 * @param topicId
	 * @param sessionId
	 */
	public synchronized void unsubscribe(String topicId, String sessionId) {
		this.removeSessionId(topicId, sessionId);
	}

	/**
	 * sessionId 의 모든 구독상태를 off
	 * 
	 * @param sessionId
	 */
	public synchronized void unsubscribeAll(String sessionId) {
		for (String topicId : this.subscription.keySet()) {
			this.removeSessionId(topicId, sessionId);
		}
	}

	/**
	 * topicid 에 연결된 sessionid 를 삭제한다.
	 * 
	 * @param topicId
	 * @param sessionId
	 */
	private void removeSessionId(String topicId, String sessionId) {
		Set<String> sessionIdSet = (Set<String>) MapUtils.getObject(this.subscription, topicId);
		if (sessionIdSet != null) {
			sessionIdSet.remove(sessionId);
		}

	}

	/**
	 * 구독된 push 목록을 반환
	 * 
	 * @return
	 */
	public List<TbIotTopicDTO> iterate() {

		HashMap<String, TbIotTopicDTO> map = new HashMap<String, TbIotTopicDTO>();
		for (TbIotTopicDTO item : this.hashedTopicList) {
			map.put(item.getTopicId(), item);
		}
		ArrayList<TbIotTopicDTO> arrayList = new ArrayList<TbIotTopicDTO>();
		for (String topicId : subscription.keySet()) {
			Set<String> sessionIdSet = subscription.get(topicId);
			if (sessionIdSet != null && !sessionIdSet.isEmpty()) {
				TbIotTopicDTO item = (TbIotTopicDTO) MapUtils.getObject(map, topicId);
				if (item != null) {
					arrayList.add(item);
				}
			}
		}
		return arrayList;
	}

	/**
	 * 기준 push 항목을 반환
	 * 
	 * @param topicId
	 * @return
	 */
	public TbIotTopicDTO findTopicItem(String topicId) {
		for (TbIotTopicDTO item : this.baseTopicList) {
			if (item.getTopicId().equals(topicId)) {
				return item;
			}
		}
		return null;
	}
}
