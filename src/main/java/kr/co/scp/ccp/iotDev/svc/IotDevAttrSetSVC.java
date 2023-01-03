package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IotDevAttrSetSVC {

	public List<TbIotDevAttrSetDTO> retrieveIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;
	
	XSSFWorkbook createIotPasteDevAttrSetTemp() throws BizException;
	public void createIotDevAttrSetAll(HttpServletRequest request) throws BizException;
	
	public void insertIotDevAttrSetAll(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException ;
	
	public Integer retrieveIotDevSetAllCdId(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public Integer retrieveIotDevSetAllCdNm(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
	public Integer retrieveIotDevPSetAllCdNm(TbIotDevAttrSetDTO tbIotDevAttrSetDTO);
}

