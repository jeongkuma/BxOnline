package kr.co.scp.ccp.iotEntrDevHist.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqListDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface IotEntrDevHistSVC {

	List<TbIotDevMdlDTO> retrieveDevClsList(TbIotEntrDevHistReqDTO tbIotEntrDevHistReqDTO);

	List<TbIotDevMdlDTO> retrieveDevMdlList(TbIotDevMdlReqDTO tbIotDevMdlReqDTO);

	HashMap<String, Object> retrieveEntrDevHistList(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO);

	XSSFWorkbook excelCreate(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO);
	
	void csvCreate(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO, HttpServletResponse response) throws BizException;

}
