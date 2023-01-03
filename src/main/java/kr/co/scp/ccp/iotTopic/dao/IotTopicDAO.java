package kr.co.scp.ccp.iotTopic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTopic.dto.SelectTopicInfoDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;

@Mapper
public interface IotTopicDAO {

	public List<TbIotTopicDTO> retrieveIotTopicList() throws BizException;

	public List<TbIotUsrDashConDTO> retrieveIotUsrDashConList() throws BizException;

	public TbIotTopicDTO findByTopicId(String topicId);

	public int insertIotTopicDTO(TbIotTopicDTO tbIotTopicDTO) throws BizException;

	public int updateIotTopicDTO(TbIotTopicDTO tbIotTopicDTO) throws BizException;

	public int deleteIotUsrDashConList(TbIotUsrDashConDTO tbIotUsrDashConDTO) throws BizException;

	public int insertIotUsrDashConList(List<TbIotUsrDashConDTO> params) throws BizException;

	public List<SelectTopicInfoDTO> getTopicList() throws BizException;

}
