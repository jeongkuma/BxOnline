package kr.co.scp.ccp.iotColSource.svc;

import java.util.HashMap;

import kr.co.scp.ccp.iotColSource.dto.TbIotColSourceDTO;

public interface IotColSourceSVC {


	HashMap<String, Object> retrieveIotColSourceList(TbIotColSourceDTO tbIotColSourceDTO);
	
	HashMap<String, Object> retrieveIotColSourceDetail(TbIotColSourceDTO tbIotColSourceDTO);





}
