package kr.co.scp.common.rule.svc;

import java.util.HashMap;
import java.util.List;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;

public interface TbIotDevColRuleSVC {

	public HashMap<String, Object> retrieveIotDevColRuleSetting(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotColAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotConAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotStaAvgAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotStaSumAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotDetNmCd(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void insertTbIoTDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;


	public void deleteTbIotDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	HashMap<String, Object> retrieveTbIotColValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	HashMap<String, Object> retrieveTbIotColTcpValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotTargetKeyParseList(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	List<HashMap> retrieveIotTargetKeyList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotRequestKeyParseList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotRequestKeyValList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotTargetKeyParseHeaderList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	List<HashMap> retrieveIotBodyType(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retriveTbIotColParseData(TbIotDevColRuleDTO tbIotDevColRuleDTO)throws BizException;

	public List<HashMap> retrieveIotServiceList( ) throws BizException;

	public void copyDeviceRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;


}
