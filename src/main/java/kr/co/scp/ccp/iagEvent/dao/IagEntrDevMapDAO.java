package kr.co.scp.ccp.iagEvent.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotEntrDevMapDTO;

@Mapper
public interface IagEntrDevMapDAO {
	public int insertTbIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public int updateTbIotEntrDevMap1(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public int updateTbIotEntrDevMap2(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public TbIotEntrDevMapDTO retrieveIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
}
