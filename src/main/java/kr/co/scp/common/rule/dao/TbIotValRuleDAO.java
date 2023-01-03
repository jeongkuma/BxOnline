package kr.co.scp.common.rule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleParamDTO;

@Mapper
public interface TbIotValRuleDAO {

	public List<TbIotCmCdOptionDTO> retrieveTbIotValRuleInfoList(TbIotValRuleDTO  tbIotValRuleDTO )  throws BizException;

	public Integer retrieveIotValRuleCnt(TbIotValRuleDTO tbIotValRuleDTO) throws BizException;

	public void insertTbIoTValRule(TbIotValRuleParamDTO tbIotParamDto) throws BizException;

	public void deleteAllTbIotValRule(TbIotValRuleDTO tbIotValRuleDTO) throws BizException;


}
