package kr.co.scp.common.push.dto;

import java.util.List;

import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TopicDTO {

	/**
	 * 관리되고 있는 base topic 주소
	 */
	private String topicId;

	/**
	 * 구독하고자하는 topic에 연결된 bean(service) 에 전달할 파라메터 (개인화 정보)
	 */
	private List<TbIotUsrDashConDTO> svcParam;

}
