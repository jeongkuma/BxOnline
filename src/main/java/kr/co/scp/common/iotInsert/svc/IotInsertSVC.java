package kr.co.scp.common.iotInsert.svc;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevListDTO;

public interface IotInsertSVC {

	public void taskDevM(TbIotInsertDevListDTO tbIotInsertDevListDTO) throws BizException;
	/*
	public void insertIotDevSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
	public String[] retrieveIotAttbSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevAttrSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public String[] retrieveIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public String[] retrieveIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public Integer retrieveIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    public void insertIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException;
    */
}
