package kr.co.scp.ccp.iagEvent.svc;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dto.IagEventCctvDTO;

public interface IagEventCctvSVC {
	public void iagEvent(IagEventCctvDTO iagEventCctvDTO, ComInfoDto comInfoDto);
	public String getFuncId(String eventCode);
}
