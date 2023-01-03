package kr.co.scp.ccp.iotPushTopic.svc.impl;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotPushTopic.dao.PushTopicDAO;
import kr.co.scp.ccp.iotPushTopic.dto.PushTopicTemperatureDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.common.push.svc.PushTopicSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 온도 조회 서비스 (푸시용)
 */
@Service
public class PushTopicTemperatureSVCImpl implements PushTopicSVC {

	@Autowired
	private PushTopicDAO pushTopicDAO;

	@Override
	public Object run(TbIotTopicDTO pushItem) throws BizException {

		PushTopicTemperatureDTO param = new PushTopicTemperatureDTO();
		List<PushTopicTemperatureDTO> result = pushTopicDAO.retrievePushTopicTemperatureList(param);
		return result.get(0);

	}

}
