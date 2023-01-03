package kr.co.scp.ccp.iotDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface IotDevSVC {

	public HashMap<String, Object> retrieveIotDev(TbIotDevDTO tbIotDevDTO) throws BizException ;
	
	public HashMap<String, Object> retrieveIotDevPar(TbIotDevDTO tbIotDevDTO) throws BizException ;

	public List<TbIotDevDTO> retrieveIotDevCls();

	public List<TbIotDevDTO> retrieveDevMdlList(TbIotDevDTO tbIotDevDTO);
 
	public void insertIotDev(TbIotDevDTO tbIotDevDTO) throws BizException ;

	public void updateIotDev(TbIotDevDTO tbIotDevDTO) throws BizException ;
	
	public void deleteIotDev(TbIotDevDTO tbIotDevDTO) throws BizException ;
//	public void custAssignmentDev(TbIotDevDTO tbIotDevDTO) throws BizException ;
	
	public void createIotDevAll(HttpServletRequest request) throws BizException;
	
	public void insertIotDevAll(TbIotDevExcelDTO tbIotDevExcelDTO) throws BizException ;

	public List<TbIotDevDTO> retrieveIotDevSvc();

	public XSSFWorkbook downloadEntrDevList(TbIotDevDTO tbIotDevDTO) throws BizException;
	public Integer retrieveIotDevAllCdId(TbIotDevDTO tbIotDevDTO);
	public Integer retrieveIotDevAllCdNm(TbIotDevDTO tbIotDevDTO);
	
	public int retrieveIotDupE(TbIotDevExcelDTO tbIotDevExcelDTO);
	public int retrieveIotMdlDupE(TbIotDevExcelDTO tbIotDevExcelDTO);
	
}


