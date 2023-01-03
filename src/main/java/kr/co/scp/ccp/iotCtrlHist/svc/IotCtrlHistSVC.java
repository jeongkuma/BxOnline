package kr.co.scp.ccp.iotCtrlHist.svc;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistConditionReqDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;

public interface IotCtrlHistSVC {

	HashMap<String, Object> retrieveIotCtrlHist(TbIotCtrlHistDTO tbIotCtrlHistDTO);

	// List<TbIotDevMdlDTO> retrieveDevClsList();

	List<TbIotDevMdlDTO> retrieveDevMdlList(TbIotDevMdlReqDTO tbIotDevMdlReqDTO);

	//List<TbIotDevMdlDTO> retrieveDevColList();

	//List<TbIotDevMdlDTO> retrieveDevPrcList();

	XSSFWorkbook excelCreate(TbIotCtrlHistDTO tbIotCtrlHistDTO);

	HashMap<String, Object> retrieveIotCtrAllcondition(TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO);

	void deleteIotCtrlList(TbIotCtrlHistDTO tbIotCtrlHistDTO) throws BizException;

}
