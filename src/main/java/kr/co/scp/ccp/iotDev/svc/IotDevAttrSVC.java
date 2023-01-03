package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



public interface IotDevAttrSVC {

	public List<TbIotDevAttrDTO> retrieveIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;
	XSSFWorkbook createIotPasteDevAttrTemp() throws BizException;
	public void createIotDevAttrAll(HttpServletRequest request) throws BizException;
	
	public void insertIotDevAttrAll(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException ;

}

