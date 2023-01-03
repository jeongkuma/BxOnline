package kr.co.scl.biz.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SensorDao {
    /**
     * 테스트용
     * @param parameters
     * @return
     */
    List<Map<String, Object>> selectListSensorMaster(Map<String, Object> parameters);
}
