package kr.co.scp.ccp.iotSelDevice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotSelDevice.dto.TbIotCDevMDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEDevCurValDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;

@Mapper
public interface IotSelDeviceDAO {

	public List<TbIotEntrDevMDTO> retrieveIotEntrDevM(TbIotEntrDevMDTO input);

	public List<TbIotEDevCurValDTO> retrieveIotDevMAtbVal(TbIotEntrDevMDTO input);

	public List<TbIotCDevMDTO> retrieveiotCDevM(TbIotEntrDevMDTO input);
	
}
