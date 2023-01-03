package kr.co.scp.common.rule.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleParamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbIotDevColRuleDAO {
	public List<TbIotDevColRuleDTO> retrieveIotDevColRuleSetting(TbIotDevColRuleDTO tbIotDevColRuleDTO)
			throws BizException;

	public Integer retrieveIotDevColRuleSettingCnt(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotColAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotConAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotStaAvgAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotStaSumAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotDetNmCd(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveIotRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void insertTbIoTDevColRule(TbIotDevColRuleParamDTO tbIotParamDto) throws BizException;

	public void upadteTbIotDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void deleteTbIotDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void deleteAllTbIotDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public Integer retrieveTbIotValRuleInfoCnt(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public String retrieveIotApiNm(Integer apiSeq) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveTbIotColValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void deleteAllTbIotDevTcpColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveTbIotColTcpValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotTargetKeyParseList(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotTargetKeyList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotRequestKeyParseList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotRequestKeyValList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotTargetKeyParseHeaderList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotBodyType(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retriveTbIotColParseData(TbIotDevColRuleDTO tbIotDevColRuleDTO)throws BizException;

	public String retrieveTbIotDevMdlVal(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotServiceList(String lang) throws BizException;

	public void deleteCopyRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;

	public void insertCopyRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException;


}
