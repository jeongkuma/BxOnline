package kr.co.scp.ccp.iotStatReport.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotStatReport.dto.TbIotComCollStatDTO;

@Mapper
public interface IotStatReportDAO {
	List<TbIotComCollStatDTO> retrieveIotStatReportList(TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException;

	List<TbIotComCollStatDTO> retrieveIotStatReportListNew(TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException;
}
