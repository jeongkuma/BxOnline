package kr.co.scp.ccp.iotDev.svc;


import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface IotDevDetSetSVC {

	public List<TbIotDevDetSetDTO> retrieveIotDevDetSet(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;
	XSSFWorkbook createIotPasteDevDetSetTemp() throws BizException;
	public void createIotDevDetSetAll(HttpServletRequest request) throws BizException;
	
	public void insertIotDevDetSetAll(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException ;


}

