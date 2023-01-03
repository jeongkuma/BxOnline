package kr.co.scp.ccp.iotDev.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetParamDTO;


@Mapper
public interface IotDevDetSetParamDAO {
   
//	public void taskIotDevDetSetParam(TbIotDevDetSetParamDTO tbIotDevDetSetParamDTO);
	public void updateTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO);
	public void insertTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO);
	public void deleteTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTOList);

} 
 