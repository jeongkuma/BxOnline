package kr.co.scp.ccp.iotDev.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;

@Mapper
public interface IotDevAttrDAO {

    public List<TbIotDevAttrDTO> retrieveIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO);

    List<TbIotDevAttrDTO> createIotPasteDevAttrTemp(TbIotDevAttrDTO tbIotDevAttrDTO);

    public void insertIotDevAttrAll(TbIotDevAttrDTO tbIotDevAttrDTO);
}
