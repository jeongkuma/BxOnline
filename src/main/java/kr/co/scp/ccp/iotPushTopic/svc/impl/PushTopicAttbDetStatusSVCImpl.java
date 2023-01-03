package kr.co.scp.ccp.iotPushTopic.svc.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotPushTopic.dao.PushTopicDAO;
import kr.co.scp.ccp.iotPushTopic.dto.PushTopicAttbDetStatusDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import kr.co.scp.common.push.svc.PushTopicSVC;

/**
 * 항목속성멸 이상항목
 */
@Service
public class PushTopicAttbDetStatusSVCImpl implements PushTopicSVC {

	@Autowired
	private PushTopicDAO pushTopicDAO;

	@Override
	public Object run(TbIotTopicDTO pushItem) throws BizException {

		HashMap<String, String> param = new HashMap<String, String>();

		List<TbIotUsrDashConDTO> listparams = pushItem.getTbIotUsrDashConList();
		for (TbIotUsrDashConDTO listparam : listparams) {
			param.put(listparam.getTmplGubun(), listparam.getTmplCondVlu());
		}
		param.put("langSet", ReqContextComponent.getComInfoDto().getXlang());
		List<PushTopicAttbDetStatusDTO> result = pushTopicDAO.retrievePushTopicAttbDetStatusList(param);
		return result;
	}

}
