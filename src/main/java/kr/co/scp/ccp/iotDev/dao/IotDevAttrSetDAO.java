package kr.co.scp.ccp.iotDev.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IotDevAttrSetDAO {
   
	public List<TbIotDevAttrSetDTO> retrieveIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	List<TbIotDevAttrSetDTO> createIotPasteDevAttrSetTemp(TbIotDevAttrSetDTO tbIotDevAttSetDTO);
	
	public void insertIotDevAttrSetAll(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;
	
	public Integer retrieveIotDevSetAllCdId(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public Integer retrieveIotDevSetAllCdNm(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public Integer retrieveIotDevPSetAllCdId(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
} 

 