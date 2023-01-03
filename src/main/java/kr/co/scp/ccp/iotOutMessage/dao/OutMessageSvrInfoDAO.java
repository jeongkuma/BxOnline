package kr.co.scp.ccp.iotOutMessage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotOutMessage.dto.TbIotOutSvrMsgSetMDTO;

@Mapper
@Repository
public interface OutMessageSvrInfoDAO {
    List<TbIotOutSvrMsgSetMDTO> retrieveOutSvrMsgList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
   
    Integer retrieveIotOutSvrMsgSetMCount(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
 
    List<TbIotOutSvrMsgSetMDTO> retrieveOutSvrMsgHList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    Integer retrieveIotOutSvrMsgSetHCount(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    List<TbIotOutSvrMsgSetMDTO> retrieveGetSvcListByseqList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    List<TbIotOutSvrMsgSetMDTO> retrieveIotGetCustList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    List<TbIotOutSvrMsgSetMDTO> retrieveIotGetDevClsList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    List<TbIotOutSvrMsgSetMDTO> retrieveIotGetDevMdlList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    List<TbIotOutSvrMsgSetMDTO> retrieveIotOutMsgSendList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    List<TbIotOutSvrMsgSetMDTO> retrieveIotOutMsgRecieveList(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    void registerOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    void registerOutSvrMsgSetHis(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    void updateOutSvrMsgSet(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    TbIotOutSvrMsgSetMDTO retrieveOutSvrMsg(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    TbIotOutSvrMsgSetMDTO retrieveOutSvrMsgH(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);

    public void deleteTbIotOutSvrMsg(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO);
    
    public int retrieveDuplicatedAdd(TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException;

}
