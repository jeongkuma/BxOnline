package kr.co.scp.common.rule.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dto.TbIotRuleDTO;
import kr.co.scp.common.rule.dto.TbIotRuleParamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbIotRuleDAO {


	public List<TbIotRuleDTO> retrieveTbIotRuleInfoList(TbIotRuleDTO  tbIotRuleDTO )  throws BizException;

	public void insertTbIoTRule(TbIotRuleParamDTO tbIotParamDto) throws BizException;

	public void deleteTbIotRule(TbIotRuleDTO tbIotRuleDTO) throws BizException;


}
