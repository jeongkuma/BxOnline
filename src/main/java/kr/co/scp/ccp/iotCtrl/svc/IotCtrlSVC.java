package kr.co.scp.ccp.iotCtrl.svc;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;

public interface IotCtrlSVC {

	HashMap<String, Object> retrieveIotCtrlList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException;
	HashMap<String, Object> retrieveTbIotCtrlRsvList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException;
	HashMap<String, Object> retrieveTbIotCtrlAttbList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException;
	HashMap<String, Object> deleteIotCtrlList(TbIotCtrlDTO tbIotCtrlDTO) throws BizException;
	HashMap<String, Object> insertIoTCtrlRsv(HttpServletRequest request, TbIotCtrlDTO tbIotCtrlDTO) throws BizException;
	HashMap<String, Object> callImmApi(HttpServletRequest request, TbIotCtrlDTO ctrlDto, List<TbIotCtrlDTO> instantList) throws BizException ;
}
