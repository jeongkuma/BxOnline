package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IotDevExcelSVC {
	XSSFWorkbook createIotPasteDev(TbIotDevExcelDTO tbIotDevExcelDTO);
	XSSFWorkbook createIotPasteDevAtb(TbIotDevExcelDTO tbIotDevExcelDTO);
	XSSFWorkbook createIotPasteDevAtbSet(TbIotDevExcelDTO tbIotDevExcelDTO);
	XSSFWorkbook createIotPasteDevDetSet(TbIotDevExcelDTO tbIotDevExcelDTO);
	int retrieveCustSeq(TbIotDevExcelDTO tbIotDevExcelDTO);
	XSSFWorkbook createIotPasteDevAttr(TbIotDevExcelDTO tbIotDevExcelDTO) throws BizException;
//	public XSSFWorkbook createIotPasteDevAttr(TbIotEDevDTO tbIotEDevDTO) throws BizException;
	XSSFWorkbook createIotPasteDevTemp() throws BizException;
	
}
