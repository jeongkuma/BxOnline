package kr.co.scp.ccp.iotDev.dao;


import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IotDevCtlDAO {

	public int retrieveIotDup(TbIotDevCtlDTO tbIotDevCtlDTO);
	public int retrieveIotMdlDup(TbIotDevCtlDTO tbIotDevCtlDTO);
//	public void  insertIotDevCopy(TbIotDevCtlDTO tbIotDevCtlDTO);
	public String[] retrieveIotAttbSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public void insertIotDevAttrSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public String[] retrieveIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public void insertIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public String[] retrieveIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public void insertIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public Integer retrieveIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
    public void insertIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
	public void insertIotDevSeq(TbIotDevCtlDTO tbIotDevCtlDTO);
	public Integer retrieveIotDevMdlValCnt(TbIotDevCtlDTO tbIotDevCtlDTO);
	public List<TbIotDevCtlDTO> retrieveIotSvcbyHd(TbIotDevCtlDTO tbIotDevCtlDTO);
	public List<TbIotDevCtlDTO> retrieveIotClsbyHd(TbIotDevCtlDTO tbIotDevCtlDTO);
    public List<TbIotDevCtlDTO> retrieveIotHdbyCls(TbIotDevCtlDTO tbIotDevCtlDTO);
}


