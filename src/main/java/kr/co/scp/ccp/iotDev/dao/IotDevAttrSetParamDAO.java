package kr.co.scp.ccp.iotDev.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetParamDTO;


@Mapper
public interface IotDevAttrSetParamDAO {
   
	public void taskIotDevAttrSetParam(TbIotDevAttrSetParamDTO tbIotDevAttrSetParamDTO);
	public void updateTbIotDevSetParam(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public void insertTbIotDevSetParam(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public void deleteTbIotDevSetParam(TbIotDevAttrSetDTO tbIotDevAttrSetDTOList);

} 
 