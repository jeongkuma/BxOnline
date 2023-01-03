package kr.co.scp.common.iotInsert.dao;

import kr.co.scp.common.iotInsert.dto.TbIotInsertDevAttbDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevAttbSetDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevDetSetDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevMDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IotInsertDAO {

	public void  insertSDevM(TbIotInsertDevMDTO tbIotInsertDevMDTO);
	public void  insertSDevAttb(TbIotInsertDevAttbDTO tbIotInsertDevAttbDTO);
	public void  insertSDevAttbSet(TbIotInsertDevAttbSetDTO tbIotInsertDevAttbSetDTO);
	public void  insertSDevDetSet(TbIotInsertDevDetSetDTO tbIotInsertDevDetbDTO);
	
	public int retrieveIotSDevCnt(TbIotInsertDevMDTO tbIotInsertDevMDTO);
	public int retrieveIotSDevAttbCnt(TbIotInsertDevAttbDTO tbIotInsertDevAttbDTO);
	public int retrieveIotSDevAttbSetCnt(TbIotInsertDevAttbSetDTO tbIotInsertDevAttbSetDTO);
	public int retrieveIotSDevDetSetCnt(TbIotInsertDevDetSetDTO tbIotInsertDevDetDTO);
	
	public void deleteIotSDevCnt(TbIotInsertDevMDTO tbIotInsertDevMDTO);
	public void deleteIotSDevAttbCnt(TbIotInsertDevAttbDTO tbIotInsertDevAttbDTO);
	public void deleteIotSDevAttbSetCnt(TbIotInsertDevAttbSetDTO tbIotInsertDevAttbSetDTO);
	public void deleteIotSDevDetSetCnt(TbIotInsertDevDetSetDTO tbIotInsertDevDetDTO);
	
//	public void  updateSDevAttb(TbIotInsertDevAttbDTO tbIotInsertDevAttbDTO);
//	public void  updateIotSDev(TbIotInsertDevMDTO tbIotInsertDevMDTO);
	
}
