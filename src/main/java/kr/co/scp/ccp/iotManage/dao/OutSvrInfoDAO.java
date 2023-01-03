package kr.co.scp.ccp.iotManage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotManage.dto.TbIotOutSvrEDTO;

@Mapper
@Repository
public interface OutSvrInfoDAO {
    List<TbIotOutSvrEDTO> retrieveOutSvrInfoMList(TbIotOutSvrEDTO tbIotOutSvrEDTO);
   
    Integer retrieveIotOutSvrInfoMCount(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    TbIotOutSvrEDTO retrieveOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    TbIotOutSvrEDTO retrieveOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);
    
    public TbIotCustUDTO retrieveIotCustBySeq(TbIotCustUDTO tbIotCustUDTO)throws BizException; 

    List<TbIotOutSvrEDTO> retrieveOutSvrInfoList(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    Integer retrieveIotOutSvrInfoCount(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void registerOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void registerOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void updateTbIotOutSvrM(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    void updateTbIotOutSvrInfo(TbIotOutSvrEDTO tbIotOutSvrEDTO);

    public int retrieveDuplicatedSvrNm(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;

    public int retrieveDuplicatedCust(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;
    
    public int retrieveMakeToken(TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException;
    
}
