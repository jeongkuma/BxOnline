package kr.co.scp.ccp.iagEvent.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagLogDTO;

@Mapper
public interface IagLogDAO {
	public void insertTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO);
	public void updateTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO);
}
