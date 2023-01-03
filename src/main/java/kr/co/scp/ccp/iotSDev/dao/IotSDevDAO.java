package kr.co.scp.ccp.iotSDev.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbSetDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevDTO;

@Mapper
public interface IotSDevDAO {

    public TbIotSDevDTO retrieveSDev(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevDTO> retrieveSDevList(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevAtbDTO> retrieveSDevAtbList(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevAtbSetDTO> retrieveSDevAtbSetList(TbIotSDevAtbDTO tbIotSDevAtbDTO);

    public List<TbIotSDevAtbDTO> retrieveSDevJoinAttbs(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevDTO> retrieveSvcDevCls(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevDTO> retrieveSvcDevClsList(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevDTO> retrieveSvcDevMdlList(TbIotSDevDTO tbIotSDevDTO);

    public List<TbIotSDevAtbSetDTO> retrieveSDevSndColPeriodList(TbIotSDevAtbDTO tbIotSDevDto);

    public List<TbIotSDevAtbDTO> retrieveSDevColAttbList(TbIotSDevDTO tbIotSDevDTO);
}
