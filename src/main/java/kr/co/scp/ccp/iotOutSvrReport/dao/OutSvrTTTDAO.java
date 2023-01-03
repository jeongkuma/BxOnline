package kr.co.scp.ccp.iotOutSvrReport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OutSvrTTTDAO {
	
	List<Map> retrieveTbIotColSvr();
	
	List<Map> retrieveTbIotOutStatOrList(Map params);
}
