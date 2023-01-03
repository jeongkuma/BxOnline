package kr.co.scp.ccp.iotCtrlHist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistConditionReqDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistResDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
@Mapper
public interface IotCtrlHistDAO {

	Integer retrieveIotCtrlCount(TbIotCtrlHistDTO tbIotCtrlHistDTO);

	List<TbIotCtrlHistResDTO> retrieveIotCtrlHist(TbIotCtrlHistDTO tbIotCtrlHistDTO);

	List<TbIotDevMdlDTO> retrieveDevClsList(TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO);

	List<TbIotDevMdlDTO> retrieveDevMdlList(TbIotDevMdlReqDTO tbIotDevMdlReqDTO);

	List<TbIotDevMdlDTO> retrieveDevColList(TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO);

	List<TbIotDevMdlDTO> retrieveDevPrcList(TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO);

	List<TbIotCtrlHistDTO> retrieveIotCtrlHistNotPage(TbIotCtrlHistDTO tbIotCtrlHistDTO);
	
	public void deleteIotCtlM(TbIotCtrlHistDTO tbIotCtrlHistDTO);
	
	public void deleteIotCtlHist(TbIotCtrlHistDTO tbIotCtrlHistDTO);

}
