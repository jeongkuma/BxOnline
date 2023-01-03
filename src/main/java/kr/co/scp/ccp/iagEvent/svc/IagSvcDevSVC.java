package kr.co.scp.ccp.iagEvent.svc;

import java.util.List;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagSvcDevDTO;

public interface IagSvcDevSVC {
	public List<TbIotIagSvcDevDTO> retrieveTbIotIagSvcDev(String devMdlCd); 
}
