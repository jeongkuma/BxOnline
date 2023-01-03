package kr.co.scp.ccp.iotSmsHist.svc;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;

public interface IotSmsReportSVC {

	HashMap<String, Object> retrieveIotSmsReportList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO) throws BizException;
	
	XSSFWorkbook excelCreate(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);
}
