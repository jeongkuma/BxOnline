package kr.co.scp.ccp.iotDev.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrParamDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;


@Mapper
public interface IotDevAttrParamDAO {
 
	public void taskIotDevAttrParam(TbIotDevAttrParamDTO tbIotDevAttrParamDTO);
	public void updateTbIotDevParam(TbIotDevAttrDTO tbIotDevAttrDTO);

	public void insertTbIotDevParam(TbIotDevAttrDTO tbIotDevAttrDTO);

	public void deleteTbIotDevParam(TbIotDevAttrDTO tbIotDevAttrDTO);
	public void deleteTbIotDevAttbSetParam(TbIotDevAttrDTO tbIotDevAttrDTO);
	public void deleteTbIotDevDetSetParam(TbIotDevAttrDTO tbIotDevAttrDTO);
	public int retrieveIotAttbSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO);
	public int retrieveIotDetSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO);
	
} 
 