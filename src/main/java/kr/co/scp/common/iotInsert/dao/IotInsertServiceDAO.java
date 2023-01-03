package kr.co.scp.common.iotInsert.dao;

import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface IotInsertServiceDAO {

	public Integer cmCdCount(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public Integer svcMCount(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public Integer deleteCmcd(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public Integer deleteSvcM(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public Integer deleteClsImg(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public void insertCmCd(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public void insertSvcM(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public void insertClsImg(TbIotInsertServiceDTO tbIotInsertServiceDTO);
	public HashMap<String,Object> retriveSvcStatus(HashMap<String,Object> hashMap);
}
