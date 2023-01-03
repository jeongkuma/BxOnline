package kr.co.scp.ccp.iagEvent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevDTO;

@Mapper
public interface IagEntrDevDAO {
	public Integer retrieveTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public List<String> retrieveTbIotIagEntrDevImg(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void insertTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void updateTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void updateTbIotIagEntrDevC17CAN(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
}
