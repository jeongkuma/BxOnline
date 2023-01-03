package kr.co.scp.ccp.iotDev.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;

@Mapper
public interface IotDevExcelDAO {
	List<TbIotDevExcelDTO> createIotPasteDev(TbIotDevExcelDTO tbIotDevExcelDTO);
	List<TbIotDevExcelDTO> createIotPasteDevAtb(TbIotDevExcelDTO tbIotDevExcelDTO);
	List<TbIotDevExcelDTO> createIotPasteDevAtbSet(TbIotDevExcelDTO tbIotDevExcelDTO);
	List<TbIotDevExcelDTO> createIotPasteDevDetSet(TbIotDevExcelDTO tbIotDevExcelDTO);
	int retrieveCustSeq(TbIotDevExcelDTO tbIotDevExcelDTO);
	List<TbIotDevExcelDTO> createIotPasteDevAttr(TbIotDevExcelDTO tbIotDevExcelDTO);
	List<TbIotDevExcelDTO> createIotPasteDevTemp(TbIotDevExcelDTO tbIotDevExcelDTO);
}
