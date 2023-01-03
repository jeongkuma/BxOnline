package kr.co.scp.ccp.iagEvent.svc;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagLogDTO;

public interface IagLogSVC {
	public void insertTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO); 
	public void updateTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO); 
}
