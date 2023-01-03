package kr.co.scp.ccp.iagEvent.svc;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dto.IagEventDTO;

public interface IagEventSVC {
	public void iagEvent(IagEventDTO iagEventDTO, ComInfoDto comInfoDto);
	public String getFuncId(String subsStusCode);
}
