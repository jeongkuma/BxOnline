package kr.co.scp.ccp.iagEvent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagSvcDevDTO;

@Mapper
public interface IagSvcDevDAO {
	public List<TbIotIagSvcDevDTO> retrieveTbIotIagSvcDev(String devMdlCd);
}
