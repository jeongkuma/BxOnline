package kr.co.scp.ccp.iotEntrDevStat.svc;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEntrDevStat.dto.TbIotEntrDevStatDTO;

public interface IotEntrDevStatSVC {
	public HashMap<String, Object> retrieveHourStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;
	public HashMap<String, Object> retrieveDayStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;
	public HashMap<String, Object> retrieveWeekStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;
	public HashMap<String, Object> retrieveMonthStatList(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;
	public XSSFWorkbook retrieveStatExcelDownload(TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException;
}

