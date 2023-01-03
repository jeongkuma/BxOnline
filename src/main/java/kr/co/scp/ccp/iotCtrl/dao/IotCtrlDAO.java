package kr.co.scp.ccp.iotCtrl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;

@Mapper
public interface IotCtrlDAO {

	int retrieveTbIotCtrlCount(TbIotCtrlDTO tbIotCtrlDTO);
	public List<TbIotCtrlDTO> retrieveTbIotCtrlList(TbIotCtrlDTO tbIotCtrlDTO);
	public List<TbIotCtrlDTO> retrieveTbIotCtrlRsvList(TbIotCtrlDTO tbIotCtrlDTO);
	public List<TbIotCtrlDTO> retrieveTbIotCtrlAttbList(TbIotCtrlDTO tbIotCtrlDTO);
	public TbIotCtrlDTO retrieveTbIotCtlMprcCd(TbIotCtrlDTO tbIotCtrlDTO);
	public void insertIotCtlM(TbIotCtrlDTO tbIotCtrlDTO);
	public void updateIotCtlM(TbIotCtrlDTO tbIotCtrlDTO);
	public void insertIotCtlHist(TbIotCtrlDTO tbIotCtrlDTO);
	public void updateIotCtlHist(TbIotCtrlDTO tbIotCtrlDTO);
	public void updateIotEntrDev(TbIotCtrlDTO tbIotCtrlDTO);
	public void deleteIotCtlM(TbIotCtrlDTO tbIotCtrlDTO);
	public void deleteIotCtlHist(TbIotCtrlDTO tbIotCtrlDTO);
	
	
	
	public void updateIotCtrlRsv(TbIotCtrlDTO tbIotCtrlDTO);
	public void updateIotCtrlHist(TbIotCtrlDTO tbIotCtrlDTO);
	public void deleteIotCtrlCur(TbIotCtrlDTO tbIotCtrlDTO);
	public Integer retrieveTbIotCtrlCurCnt(TbIotCtrlDTO tbIotCtrlDTO);
	public void insertIotCtlRsv(TbIotCtrlDTO tbIotCtrlDTO);
//	public void insertIotCtlHist(TbIotCtrlDTO tbIotCtrlDTO);
	public void insertIotCtlCur(TbIotCtrlDTO tbIotCtrlDTO);
	public void updateIotCtlCur(TbIotCtrlDTO tbIotCtrlDTO);
}
