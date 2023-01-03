package kr.co.scp.common.rule.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbIotDevApiRuleDAO {

	public List<TbIotCmCdOptionDTO> retrieveTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

	public List<TbIotCmCdOptionDTO> retrieveTbIotDevApiRuleInputList(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

	public void insertTbIotDevApiRule(TbIotDevApiRuleParamDTO tbIotDevApiRuleParamDTO) throws BizException;

	public void deleteAllTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException;

}
