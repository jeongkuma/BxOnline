package kr.co.scp.ccp.iotColSource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotColSource.dto.TbIotColSourceDTO;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistConditionReqDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistResDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
@Mapper
public interface IotColSourceDAO {

	Integer retrieveIotColSourceCount(TbIotColSourceDTO tbIotColSourceDTO);

	List<TbIotColSourceDTO> retrieveIotColSourceList(TbIotColSourceDTO tbIotColSourceDTO);
	
	TbIotColSourceDTO retrieveIotColSourceDetail(TbIotColSourceDTO tbIotColSourceDTO);
	


}
