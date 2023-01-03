package kr.co.scp.ccp.iotPushTopic.dto;

import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class PushTopicPollingDTO {

	private String topicId;
	private List<TbIotUsrDashConDTO> svcParam;

}
