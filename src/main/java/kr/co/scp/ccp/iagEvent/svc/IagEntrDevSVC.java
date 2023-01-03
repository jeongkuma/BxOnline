package kr.co.scp.ccp.iagEvent.svc;

import java.util.List;

import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevDTO;

public interface IagEntrDevSVC {
	public Integer retrieveTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public List<String> retrieveTbIotIagEntrDevImg(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void insertTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void updateTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
	public void updateTbIotIagEntrDevC17CAN(TbIotIagEntrDevDTO tbIotIagEntrDevDTO);
}
