package kr.co.scp.ccp.iotManage.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotManage.dto.TbIotOutSvrEDTO;

public interface OutSvrInfoService {
    HashMap<String, Object> retrieveIotOutSvrInfoMList(TbIotOutSvrEDTO tbIotOutSvrEDTO);
   
    TbIotOutSvrEDTO retrieveIotOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    TbIotOutSvrEDTO retrieveIotOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    HashMap<String, Object> retrieveIotOutSvrInfoList(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    public HashMap<String, Object> retrieveIotCustBySeq(TbIotCustUDTO tbIotCustDTO) throws BizException;
	
    void insertTbIoTOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void insertTbIoTOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void updateTbIoTOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void updateTbIoTOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);
    
    public int retrieveDuplicatedSvrNm(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;
    
    public int retrieveDuplicatedCust(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;
    
    public int retrieveMakeToken(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;
    
}
