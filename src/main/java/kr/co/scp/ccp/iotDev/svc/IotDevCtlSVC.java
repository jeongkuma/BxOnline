package kr.co.scp.ccp.iotDev.svc;


import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;

import java.util.List;

public interface IotDevCtlSVC {

	public int retrieveIotDup(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException ;
	public int retrieveIotMdlDup(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException ;

	public void insertIotDevCopy(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException ;

	public void insertIotDevSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
	public String[] retrieveIotAttbSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevAttrSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public String[] retrieveIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public String[] retrieveIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public Integer retrieveIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public List<TbIotDevCtlDTO> retrieveIotSvcbyHd(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public List<TbIotDevCtlDTO> retrieveIotClsbyHd(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public List<TbIotDevCtlDTO> retrieveIotHdbyCls(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
	public int retrieveIotDevMdlValCnt(TbIotDevCtlDTO tbIotDevCtl1DTO) throws BizException;
}

