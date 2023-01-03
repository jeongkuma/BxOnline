package kr.co.scp.ccp.iotEntrDevHist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqListDTO;

@Mapper
public interface IotEntrDevHistDAO {

	List<TbIotDevMdlDTO> retrieveDevClsList(TbIotEntrDevHistReqDTO tbIotEntrDevHistReqDTO);

	List<TbIotEntrDevHistDTO> retrieveEntrDevHistList(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO);

	Integer retrieveEntrDevHistCount(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO);

	List<TbIotEntrDevHistDTO> retrieveEntrDevHistNotPage(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO);

}
