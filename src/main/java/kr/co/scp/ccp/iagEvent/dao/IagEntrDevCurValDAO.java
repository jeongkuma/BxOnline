package kr.co.scp.ccp.iagEvent.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevCurValDTO;

@Mapper
public interface IagEntrDevCurValDAO {
	public void insertTbIotIagEntrDevCurVal(TbIotIagEntrDevCurValDTO tbIotIagEntrDevCurValDTO);
}
