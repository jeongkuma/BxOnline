package kr.co.scp.ccp.iotSelDevice.svc;

import java.util.List;
import java.util.Map;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEDevCurValDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;

public interface IotSelDeviceSVC {

	public Map<String, Object> retrieveIotSelDevice(TbIotEntrDevMDTO input) throws BizException;

	public List<TbIotEDevCurValDTO> retrieveIotSelDeviceDetail(TbIotEntrDevMDTO input) throws BizException;
	
}