package kr.co.scp.ccp.iagEvent.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;

@Mapper
public interface IagEntrDevMDAO {
	public int insertTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO);
	public int updateTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO);
	public int deleteTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO);
	public TbIotEntrDevMDTO retrieveTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO);
}
