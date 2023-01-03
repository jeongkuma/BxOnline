package kr.co.scp.ccp.iotPushTopic.svc.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotPushTopic.dao.PushTopicDAO;
import kr.co.scp.ccp.iotPushTopic.dto.PushTopicDevClsStatusDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import kr.co.scp.common.push.svc.PushTopicSVC;

/**
 * 장비유형별장비현황
 */
@Service
public class PushTopicDevClsStatusSVCImpl implements PushTopicSVC {

	@Autowired
	private PushTopicDAO pushTopicDAO;

	@Override
	public Object run(TbIotTopicDTO pushItem) throws BizException {
		HashMap<String, String> param = new HashMap<String, String>();

		List<TbIotUsrDashConDTO> listparams = pushItem.getTbIotUsrDashConList();
		for (TbIotUsrDashConDTO listparam : listparams) {
			param.put(listparam.getTmplGubun(), listparam.getTmplCondVlu());
		}
		List<PushTopicDevClsStatusDTO> result = pushTopicDAO.retrievePushTopicDevClsStatusList(param);
		return result;

	}

}
