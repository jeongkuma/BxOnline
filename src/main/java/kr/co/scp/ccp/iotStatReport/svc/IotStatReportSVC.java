package kr.co.scp.ccp.iotStatReport.svc;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotStatReport.dto.TbIotComCollStatDTO;

public interface IotStatReportSVC {

	public Map<String, Object> retrieveIotStatReportList(TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException;

	public Map<String, Object> retrieveIotStatReportListNew(TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException;

	public XSSFWorkbook downloadIotStatReportList(TbIotComCollStatDTO tbIotComCollStatDTO) throws Exception ;

}
