package kr.co.scp.ccp.iagEvent.svc;

import kr.co.scp.ccp.iagEvent.dto.TbIotEntrDevMapDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;

public interface IagEntrDevMSVC {
	public int insertTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO); 
	public int updateTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO);
	public int deleteTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO); 
	public TbIotEntrDevMDTO retrieveTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO); 
	public int insertTbIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public int updateTbIotEntrDevMap1(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public int updateTbIotEntrDevMap2(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
	public TbIotEntrDevMapDTO retrieveIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO);
}
