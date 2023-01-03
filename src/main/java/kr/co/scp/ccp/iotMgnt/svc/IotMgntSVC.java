package kr.co.scp.ccp.iotMgnt.svc;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevDetSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevMDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;

public interface IotMgntSVC {
	
	HashMap<String, Object> retriveService(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveServiceList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveServiceAuthList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevAtbList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevAtbSetList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevDetSetList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevRuleParseList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevRuleValList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevRuleMapList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevRuleServiceList(IotMgntDTO iotMgntDTO) throws BizException;

	XSSFWorkbook retriveDevRuleApiList(IotMgntDTO iotMgntDTO) throws BizException;

	void svcUploadSvc(HttpServletRequest request) throws BizException;

	void svcRoleMapUploadSvc(HttpServletRequest request) throws BizException;

	void deviceUploadSvc(HttpServletRequest request) throws BizException;

	void deviceAttrUploadSvc(HttpServletRequest request) throws BizException;

	void deviceAttrSetUploadSvc(HttpServletRequest request) throws BizException;

	void deviceDetSetUploadSvc(HttpServletRequest request) throws BizException;

	void svcRoleRuleParseUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO);

	void svcRoleRuleApiSvcUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO);

	void svcRoleRuleValUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO) throws BizException;

	void apiValUploadSvc(HttpServletRequest request) throws BizException;
	
	
	
	HashMap<String, Object> retriveServiceListSvc(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotRoleMapDTO> retriveServiceAuthListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevMDTO> retriveDevListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevAtbDTO> retriveDevAtbListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevAtbSetDTO> retriveDevAtbSetListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevDetSetDTO> retriveDevDetSetListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevColRuleDTO> retriveDevRuleParseListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotValRuleDTO> retriveDevRuleValListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevColRuleDTO> retriveDevRuleMapListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevApiRuleParamDTO> retriveDevRuleServiceListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotValRuleDTO> retriveDevRuleApiListQuery(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevMDTO> retrieveIotMdlList(IotMgntDTO iotMgntDTO) throws BizException;

}