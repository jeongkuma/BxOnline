package kr.co.scp.ccp.iotMgnt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntApiRuleDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntColRuleDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntRoleMapDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevDetSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevMDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSvcDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntValRuleDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;

@Mapper
public interface IotMgntDAO {
	List<TbIotSvcDto> retriveService(IotMgntDTO iotMgntDTO) throws BizException;
	
	List<IotMgntSvcDTO> retriveServiceList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotRoleMapDTO> retriveServiceAuthList(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevMDTO> retriveDevList(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevAtbDTO> retriveDevAtbList(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevAtbSetDTO> retriveDevAtbSetList(IotMgntDTO iotMgntDTO) throws BizException;

	List<IotMgntSDevDetSetDTO> retriveDevDetSetList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevColRuleDTO> retriveDevRuleParseList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotValRuleDTO> retriveDevRuleValList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevColRuleDTO> retriveDevRuleMapList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotDevApiRuleParamDTO> retriveDevRuleServiceList(IotMgntDTO iotMgntDTO) throws BizException;

	List<TbIotValRuleDTO> retriveDevRuleApiList(IotMgntDTO iotMgntDTO) throws BizException;

	void insertServiceM(TbIotSvcDto trimObj_Service) throws BizException;

	void insertRoleMap(IotMgntRoleMapDTO roleMapDto) throws BizException;

	void deleleteRoleMapAll() throws BizException;

	void deleleteServiceMAll() throws BizException;

	void deleleteDeviceMAll(IotMgntSDevMDTO deleteDto) throws BizException;

	void insertDevM(IotMgntSDevMDTO devMDto) throws BizException;

	void deleleteDeviceMAttr(IotMgntSDevAtbDTO deleteDto) throws BizException;

	void insertDevAttr(IotMgntSDevAtbDTO devAttrDto) throws BizException;

	void deleleteDeviceMAttrSet(IotMgntSDevAtbSetDTO deleteDto) throws BizException;

	void insertDevAttrSet(IotMgntSDevAtbSetDTO devAttrSetDto) throws BizException;

	void insertDevDetSet(IotMgntSDevDetSetDTO devDetSetDto) throws BizException;

	void deleleteDeviceDetSet(IotMgntSDevDetSetDTO deleteDto) throws BizException;

	void deleleteRuleParseAll(IotMgntDTO iotMgntDTO) throws BizException;

	void deleleteRuleMapAll(IotMgntDTO iotMgntDTO) throws BizException;

	void insertRuleCol(IotMgntColRuleDTO ruleColDto) throws BizException;

	void deleleteValRuleAll(IotMgntDTO iotMgntDTO) throws BizException;

	void insertRuleVal(IotMgntValRuleDTO ruleValDto) throws BizException;

	void deleleteRuleApiRuleAll(IotMgntDTO iotMgntDTO) throws BizException;

	void insertRuleApi(IotMgntApiRuleDTO ruleApiDto) throws BizException;

	void deleleteApiValRuleAll() throws BizException;
	
	public List<HashMap<String, Object>> retrieveSvcCdForSev(IotMgntDTO iotMgntDTO) throws BizException;
	
	public ArrayList<HashMap<String, Object>> retrieveClsImgForSev(HashMap hashMap) throws BizException;

	List<IotMgntSDevMDTO> retrieveIotMdlList(IotMgntDTO iotMgntDTO)  throws BizException;

}
