package kr.co.scp.common.iotInsert.dao;

import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceMappigDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceRuleServiceDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceValidDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IotInsertServiceRuleDAO {

	public Integer parseCount(TbIotInsertServiceMappigDTO tbIotInsertServiceMappigDTO);
	public void insertParseInfo(TbIotInsertServiceMappigDTO tbIotInsertServiceMappigDTO);
	public void updateParseInfo(TbIotInsertServiceMappigDTO tbIotInsertServiceMappigDTO);
	public void deleteParseInfo(TbIotInsertServiceMappigDTO tbIotInsertServiceMappigDTO);

	public void insertParseInfoAll(List<TbIotInsertServiceMappigDTO> params);
	public void deleteParseInfoAll(List<TbIotInsertServiceMappigDTO> params);
	
	public Integer validCount(TbIotInsertServiceValidDTO tbIotInsertServiceRuleDTO);
	public void insertValidInfo(TbIotInsertServiceValidDTO tbIotInsertServiceRuleDTO);
	public void updateValidInfo(TbIotInsertServiceValidDTO tbIotInsertServiceRuleDTO);
	public void deleteValidInfo(TbIotInsertServiceValidDTO tbIotInsertServiceRuleDTO);
	
	public void insertValidInfoAll(List<TbIotInsertServiceValidDTO> params);
	public void deleteValidInfoAll(List<TbIotInsertServiceValidDTO> params);

	public Integer ruleserviceCount(TbIotInsertServiceRuleServiceDTO tbIotInsertServiceRuleServiceDTO);
	public void insertRuleserviceInfo(TbIotInsertServiceRuleServiceDTO tbIotInsertServiceRuleServiceDTO);
	public void updateRuleserviceInfo(TbIotInsertServiceRuleServiceDTO tbIotInsertServiceRuleServiceDTO);
	public void deleteRuleserviceInfo(TbIotInsertServiceRuleServiceDTO tbIotInsertServiceRuleServiceDTO);
	
	public void insertRuleserviceInfoAll(List<TbIotInsertServiceRuleServiceDTO> params);
	public void deleteRuleserviceInfoAll(List<TbIotInsertServiceRuleServiceDTO> params);
		 
}
