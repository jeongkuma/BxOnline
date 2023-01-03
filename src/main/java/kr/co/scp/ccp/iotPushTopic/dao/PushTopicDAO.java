package kr.co.scp.ccp.iotPushTopic.dao;

import kr.co.scp.ccp.iotPushTopic.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface PushTopicDAO {

	public List<PushTopicDevClsStatusDTO> retrievePushTopicDevClsStatusList(Map<String, String> param);

	public List<PushTopicLevelDetStatusDTO> retrievePushTopicLevelDetStatusList(Map<String, String> param);

	public List<PushTopicAttbDetStatusDTO> retrievePushTopicAttbDetStatusPieList(Map<String, String> param);

	public List<PushTopicDevClsStatusDTO> retrievePushTopicDevClsStatusPieList(Map<String, String> param);

	public List<PushTopicLevelDetStatusDTO> retrievePushTopicLevelDetStatusPieList(Map<String, String> param);

	public List<PushTopicAttbDetStatusDTO> retrievePushTopicAttbDetStatusList(Map<String, String> param);
	
	// sample
	public List<PushTopicHumidityDTO> retrievePushTopicHumidityList(PushTopicHumidityDTO pushTopicHumidityDTO);

	// sample
	public List<PushTopicTemperatureDTO> retrievePushTopicTemperatureList(PushTopicTemperatureDTO pushTopicTemperatureDTO);

	public List<HashMap> retrievePushTopicAttbColStatusList(HashMap<String, String> param);

	public List<HashMap> retrievePushTopicAttbCurrentColStatusList(HashMap<String, String> param);

	public List<HashMap> retrievePushTopicAttbCurrentStatusList(HashMap<String, String> param);


}
