package kr.co.scp.ccp.iotTopic.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTopic.dto.SelectTopicInfoDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotTopicDTO;
import kr.co.scp.ccp.iotTopic.dto.TbIotUsrDashConDTO;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
public interface IotTopicSVC {

	/**
	 * 전체 topic 목록 조회
	 * 
	 * 개인화된 검색조건도 함께 구성.(List<TbIotUsrDashConDTO>)
	 * 
	 * @return
	 * @throws BizException
	 */
	public List<TbIotTopicDTO> retrieveIotTopicList() throws BizException;

	/**
	 * topic 검색
	 * 
	 * @param topicId
	 * @return
	 * @throws BizException
	 */
	public TbIotTopicDTO findByTopicId(String topicId) throws BizException;

	/**
	 * 토픽 저장
	 * 
	 * @param tbIotTopicDTO
	 * @throws BizException
	 */
	public void saveIotTopicDTO(TbIotTopicDTO tbIotTopicDTO) throws BizException;

	/**
	 * 개인화 정보 저장
	 * 
	 * @param basePushItem 기준(base) topic 항목
	 * @param tbIotUsrDashConList 검색조건 리스트
	 */
	public void saveIotUsrDashConList(TbIotTopicDTO basePushItem, List<TbIotUsrDashConDTO> tbIotUsrDashConList);

	public List<SelectTopicInfoDTO> getTopicList() throws BizException;

}
