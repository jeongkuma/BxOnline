package kr.co.scp.ccp.iotMapInfra.svc;

import java.util.Map;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotMapInfra.dto.MapBaseDTO;

public interface MapBaseSVC {

	public Map apiCall(MapBaseDTO input) throws BizException;

}