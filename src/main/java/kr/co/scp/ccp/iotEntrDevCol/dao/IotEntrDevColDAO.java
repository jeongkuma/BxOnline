package kr.co.scp.ccp.iotEntrDevCol.dao;

import kr.co.scp.ccp.iotEntrDevCol.dto.TbIotEDevColValDTO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IotEntrDevColDAO {

	public Integer retrieveEntrDevColCount(TbIotEDevColValDTO tbIotEDevColValDTO);
	
	public List<TbIotEDevColValDTO> retrieveEntrDevColList(TbIotEDevColValDTO tbIotEDevColValDTO);

	public List<TbIotEDevColValDTO> retrieveDownEntrDevColVals(TbIotEDevColValDTO tbIotEDevColValDTO);

	public List<TbIotJqDataResponseDTO> retrieveEntrDevTmplHeader(TbIotEDevColValDTO tbIotEDevColValDTO);

}
