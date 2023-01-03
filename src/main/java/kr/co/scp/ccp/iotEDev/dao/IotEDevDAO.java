package kr.co.scp.ccp.iotEDev.dao;

import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDetHistDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IotEDevDAO {

	public int retrieveIotEDevTCnt(TbIotEDevDTO tbIotEDevDTO);

	public List<TbIotEDevDTO> retrieveIotEDev(TbIotEDevDTO tbIotEDevDTO);

	public List<TbIotEDevDTO> retrieveIotEDevAttb(TbIotEDevDTO tbIotEDevDTO);

	public List<TbIotEDevDTO> retrieveIotEDevAddr(TbIotEDevDTO tbIotEDevDTO);

	public List<TbIotEDevDTO> retrieveIotEDevSend(TbIotEDevDTO tbIotEDevDTO);

	public TbIotEDevDTO retrieveCustInfoByCtn(TbIotCustDTO tbIotCustDTO);

	public List<TbIotEDetHistDTO> retrieveTemplateList(TbIotEDetHistDTO tbIotEDetHistDTO);

	public List<TbIotEDetHistDTO> retrieveIotDevCls();

	public List<TbIotEDetHistDTO> retrieveIotDetStatus();

	public List<TbIotEDetHistDTO> retrieveDevMdlList(TbIotEDetHistDTO tbIotEDetHistDTO);

	public List<TbIotEDetHistDTO> retrieveIotEntrDevDetList(TbIotEDetHistDTO tbIotEDetHistDTO);

	public int retrieveIotEntrDevDetCount(TbIotEDetHistDTO tbIotEDetHistDTO);

	public int retrieveIotEDevTCntDoor(TbIotEDevDTO tbIotEDevDTO);

	public List<TbIotEDevDTO> retrieveIotEDevDoor(TbIotEDevDTO tbIotEDevDTO);

}
