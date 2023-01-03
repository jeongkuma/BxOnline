package kr.co.scp.ccp.iotOutMessage.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotOutMessage.dto.TbIotOutSvrMsgSetMDTO;

public interface OutMessageSvrInfoService {
    HashMap<String, Object> retrieveIotOutSvrMsgList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    HashMap<String, Object> retrieveIotOutSvrMsgHList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    HashMap<String, Object> retrieveGetSvcListByseqList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    HashMap<String, Object> retrieveIotGetCustList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    HashMap<String, Object> retrieveIotGetDevClsList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    HashMap<String, Object> retrieveIotGetDevMdlList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    HashMap<String, Object> retrieveIotOutMsgSendList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    HashMap<String, Object> retrieveIotOutMsgRecieveList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    void insertTbIoTOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    void updateTbIoTOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    TbIotOutSvrMsgSetMDTO retrieveIotOutSvrMsg(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    TbIotOutSvrMsgSetMDTO retrieveIotOutSvrMsgH(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    public XSSFWorkbook excelCreate(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException;

    public  boolean deleteTbIoTOutSvrMsg(HttpServletRequest request,TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    public int retrieveDuplicatedAdd(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException;
   
}
