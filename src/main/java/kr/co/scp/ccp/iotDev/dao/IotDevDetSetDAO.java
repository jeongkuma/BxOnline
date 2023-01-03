package kr.co.scp.ccp.iotDev.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IotDevDetSetDAO {
    
	public List<TbIotDevDetSetDTO> retrieveIotDevDetSet(TbIotDevDetSetDTO tbIotDevDetSetDTO);
//	List<TbIotDevDetSetDTO> createIotPasteDevDetSetTemp(TbIotDevDetSetDTO tbIotDevDetSetDTO);
	public void insertIotDevDetSetAll(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;
} 
 