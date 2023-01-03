package kr.co.scp.ccp.iotDev.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



@Mapper
public interface IotDevDAO {

	public List<TbIotDevDTO> retrieveIotDev(TbIotDevDTO tbIotDevDTO);
	public List<TbIotDevDTO> retrieveIotDevPar(TbIotDevDTO tbIotDevDTO);
	public List<TbIotDevDTO> retrieveIotDevCls();
	public List<TbIotDevDTO> retrieveDevMdlList(TbIotDevDTO tbIotDevDTO);
	public List<TbIotDevDTO> retrieveIotDevSvc(TbIotSvcDto svcDto);
	public void insertIotDev(TbIotDevDTO tbIotDevDTO);
	public void updateIotDev(TbIotDevDTO tbIotDevDTO);
	public void updateIotDevAtb(TbIotDevDTO tbIotDevDTO);
	public void deleteIotDev(TbIotDevDTO tbIotDevDTO);
	//public void custAssignmentDev(TbIotDevDTO tbIotDevDTO);
	public int retrieveIotDevCount(TbIotDevDTO tbIotDevDTO);
	public int retrieveIotDevCount1(TbIotDevDTO tbIotDevDTO);
	public void insertIotDevAll(TbIotDevExcelDTO tbIotDevExcelDTO) throws BizException ;
	public  int retrieveIotDevAllCdId(TbIotDevExcelDTO tbIotDevExcelDTO);
	public  int retrieveIotDevAllCdNm(TbIotDevExcelDTO tbIotDevExcelDTO) ;

	public  void updateDlsCdNm(TbIotDevDTO TbIotDevDTO) ;

	public int retrieveIotDupE(TbIotDevExcelDTO tbIotDevExcelDTO);
	public int retrieveIotMdlDupE(TbIotDevExcelDTO tbIotDevExcelDTO);

}



