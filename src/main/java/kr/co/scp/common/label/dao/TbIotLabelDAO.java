package kr.co.scp.common.label.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.label.dto.TbIotLabelDTO;
import kr.co.scp.common.label.dto.TbIotLabelViewDTO;

@Mapper
public interface TbIotLabelDAO {
	
	public List<TbIotLabelDTO> retrieveTbIotLabelList(TbIotLabelDTO tbIotLabelDto) throws BizException;

	public void deleteTbIotLabel(TbIotLabelDTO tbIotLabelDto) throws BizException;

	public void updateTbIotLabelDTO(TbIotLabelDTO tbIotLabelDto) throws BizException;

	public void insertTbIotLabelDTO(TbIotLabelDTO tbIotLabelDto) throws BizException;

	public List<TbIotLabelViewDTO> retrieveTbIotLabelView(TbIotLabelDTO tbIotLabelDto) throws BizException;
	
	
}
