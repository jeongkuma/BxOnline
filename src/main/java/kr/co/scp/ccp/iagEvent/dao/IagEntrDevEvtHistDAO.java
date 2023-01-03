package kr.co.scp.ccp.iagEvent.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevEvtHistDTO;

@Mapper
public interface IagEntrDevEvtHistDAO {
	public void insertTbIotIagEntrDevEvtHist(TbIotIagEntrDevEvtHistDTO tbIotIagEntrDevEvtHistDTO);
}
