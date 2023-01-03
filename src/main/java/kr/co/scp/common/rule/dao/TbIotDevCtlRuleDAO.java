package kr.co.scp.common.rule.dao;

import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleParamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbIotDevCtlRuleDAO {

	List<TbIotCmCdOptionDTO> retrieveTbIotCtlValRuleInfo(TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO);
	
	void insertTbIoTDevCtlRule(TbIotDevCtlRuleParamDTO tbIotParamDto);
	
}
