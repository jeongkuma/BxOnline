package kr.co.scp.ccp.iotPushTopic.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotPushTopic.dao.PushTopicDAO;
import kr.co.scp.ccp.iotPushTopic.dto.PushTopicHumidityDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.common.push.svc.PushTopicSVC;

/**
 * 습도 조회 서비스 (푸시용)
 */
@Service
public class PushTopicHumiditySVCImpl implements PushTopicSVC {

	@Autowired
	private PushTopicDAO pushTopicDAO;

	@Override
	public Object run(TbIotTopicDTO pushItem) throws BizException {

		PushTopicHumidityDTO param = new PushTopicHumidityDTO();
		List<PushTopicHumidityDTO> result = pushTopicDAO.retrievePushTopicHumidityList(param);
		return result.get(0);

	}

}
