package kr.co.scp.ccp.iotTopic.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTopic.dao.IotTopicDAO;
import kr.co.scp.ccp.iotTopic.dto.SelectTopicInfoDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import kr.co.scp.ccp.iotTopic.svc.IotTopicSVC;
import kr.co.auiot.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class IotTopicSVCImpl implements IotTopicSVC {

	@Autowired
	private IotTopicDAO iotTopicDAO;

	@Override
	public List<TbIotTopicDTO> retrieveIotTopicList() throws BizException {
		List<TbIotTopicDTO> list = iotTopicDAO.retrieveIotTopicList();
		List<TbIotUsrDashConDTO> conds = iotTopicDAO.retrieveIotUsrDashConList();
		HashMap<String, TbIotTopicDTO> map = new HashMap<String, TbIotTopicDTO>();
		for (TbIotTopicDTO item : list) {
			map.put(item.getTopicId(), item);
		}
		for (TbIotUsrDashConDTO con : conds) {
			TbIotTopicDTO item = (TbIotTopicDTO) MapUtils.getObject(map, con.getTopicId());
			if (item != null) {
				List<TbIotUsrDashConDTO> conlist = item.getTbIotUsrDashConList();
				if (conlist == null) {
					conlist = new ArrayList<TbIotUsrDashConDTO>();
					item.setTbIotUsrDashConList(conlist);
				}
				conlist.add(con);
			}
		}
		return list;
	}

	@Override
	public TbIotTopicDTO findByTopicId(String topicId) throws BizException {
		return iotTopicDAO.findByTopicId(topicId);
	}

	@Override
	public void saveIotTopicDTO(TbIotTopicDTO tbIotTopicDTO) throws BizException {
		String userId = AuthUtils.getUser().getUserId();
		tbIotTopicDTO.setRegUserId(userId);

		// 토픽 저장.
		int cnt = iotTopicDAO.updateIotTopicDTO(tbIotTopicDTO);
		if (cnt == 0) {
			iotTopicDAO.insertIotTopicDTO(tbIotTopicDTO);
		}

	}

	@Override
	public void saveIotUsrDashConList(TbIotTopicDTO tbIotTopicDTO, List<TbIotUsrDashConDTO> tbIotUsrDashConList) throws BizException {
		if (tbIotUsrDashConList != null && tbIotUsrDashConList.size() > 0) {
			String userId = AuthUtils.getUser().getUserId();
			String userSeq = AuthUtils.getUser().getUserSeq();

			for (TbIotUsrDashConDTO param : tbIotUsrDashConList) {
				param.setTopicId(tbIotTopicDTO.getTopicId());
				param.setUsrSeq(userSeq);
				param.setRegUsrId(userId);
			}

			iotTopicDAO.deleteIotUsrDashConList(tbIotUsrDashConList.get(0));
			iotTopicDAO.insertIotUsrDashConList(tbIotUsrDashConList);
		}

	}

	@Override
	public List<SelectTopicInfoDTO> getTopicList() throws BizException {
		// TODO Auto-generated method stub
		List<SelectTopicInfoDTO> list = iotTopicDAO.getTopicList();
		return list;
	}

}
