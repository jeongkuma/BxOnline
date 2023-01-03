package kr.co.scp.ccp.common.smsTran.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.common.smsTran.dto.TbIotSmsTranDTO;

@Mapper
public interface SmsTranDAO {
	public void insertTbIotSmsTran(TbIotSmsTranDTO tbIotSmsTranDTO);
}
