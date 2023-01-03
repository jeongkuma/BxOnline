package kr.co.scp.ccp.iotEntrDevCol.svc;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.scp.ccp.iotEntrDevCol.dto.TbIotEDevColValDTO;

public interface IotEntrDevColSVC {

	public HashMap<String, Object> retrieveEntrDevColList(TbIotEDevColValDTO tbIotEDevColValDTO);

	public XSSFWorkbook downloadEntrDevColList(TbIotEDevColValDTO tbIotEDevColValDTO);

}
