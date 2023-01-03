package kr.co.scp.ccp.iotSDev.svc;

import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevDTO;

import java.util.HashMap;
import java.util.List;


public interface IotSDevSVC {

	public TbIotSDevDTO retrieveSDev(TbIotSDevDTO tbIotSDevDTO);

	public List<TbIotSDevDTO> retrieveSDevList(TbIotSDevDTO tbIotSDevDTO);

	public List<TbIotSDevAtbDTO> retrieveSDevAttb(TbIotSDevDTO tbIotSDevDTO);

	public List<TbIotSDevDTO> retrieveSvcDevClsList(TbIotSDevDTO tbIotSDevDTO);

	public List<TbIotSDevDTO> retrieveSvcDevMdlList(TbIotSDevDTO tbIotSDevDTO);

	public List<TbIotSDevAtbDTO> retrieveSDevJoinAttbs(TbIotSDevDTO tbIotSDevDTO);

	public HashMap<String, Object> retrieveSDevSndColPeriodList(TbIotSDevAtbDTO tbIotSDevDto);

	public List<TbIotSDevAtbDTO> retrieveSDevColAttb(TbIotSDevDTO tbIotSDevDTO);

}
