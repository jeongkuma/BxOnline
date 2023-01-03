package kr.co.scp.ccp.iotSmsHist.svc;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsConditiontReqDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;

public interface IotSmsTranHistSVC {

	HashMap<String, Object> retrieveIotAlarmHistList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO) throws BizException;

	HashMap<String, Object> retrieveIotAlarmConditionList(TbIotSmsConditiontReqDTO tbIotSmsConditiontReqDTO);

	XSSFWorkbook excelCreate(TbIotSmsTranHistDTO tbIotSmsTranHistDTO);

}
