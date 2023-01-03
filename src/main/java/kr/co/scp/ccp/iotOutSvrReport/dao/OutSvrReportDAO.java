package kr.co.scp.ccp.iotOutSvrReport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.scp.ccp.iotOutSvrReport.dto.TbIotOutSvrReportDTO;

@Mapper
@Repository
public interface OutSvrReportDAO {
	
	/* 고객사리스트 */
	public List<TbIotOutSvrReportDTO> custNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
	/* 서비스명리스트 */
	public List<TbIotOutSvrReportDTO> svcNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
	/* 장비유형리스트 */
	public List<TbIotOutSvrReportDTO> devClsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
	/* 장비모델리스트 */
	public List<TbIotOutSvrReportDTO> devMdlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
	/* 가입장비리스트 */
	public List<TbIotOutSvrReportDTO> entrDevList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
	/* 외부서버리스트 */
	public List<TbIotOutSvrReportDTO> outSvrList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
	
    /* 외부서버송수신이력조회 */
    public List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtReportList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    public Integer retrieveIotOutSvrRtReportCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
	/* 외부서버송수신현황-전문상세내역 */
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportDtlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 외부서버송수신이력조회 - 팝업*/
    public List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtReportListPop(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    public Integer retrieveIotOutSvrRtReportPopCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);

    /* 외부서버송수신이력조회 - 팝업 - 전문상세내역*/
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportListPopDtl(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 모델별전송현황조회 */
    public List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtMdlReportList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    public Integer retrieveIotOutSvrRtMdlReportCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 가동률 - 고객사리스트*/
    public List<Map> retrieveTbIotColSvr(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);

    /* 가동률 */
    public List<Map> retrieveIotOutSvrRtOprtnRate(Map params);
    
    public Integer retrieveIotOutSvrRtOprtnRateCount(Map params);
    
	/* 통신현황 - 고객사리스트 */
    public List<TbIotOutSvrReportDTO> cmnctSttsCustNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황 - 서비스명리스트 */
    public List<TbIotOutSvrReportDTO> cmnctSttsSvcNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황 - 외부서버명리스트 */
    public List<TbIotOutSvrReportDTO> cmnctSttsOutSvrList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황 - 장비유형리스트 */
    public List<TbIotOutSvrReportDTO> cmnctSttsDevClsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황 - 장비모델리스트 */
    public List<TbIotOutSvrReportDTO> cmnctSttsDevMdlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황조회 */
    public List<TbIotOutSvrReportDTO> retrieveIotCmnctSttsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);

    public Integer retrieveIotCmnctSttsListCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 통신현황상세조회 */
    public List<TbIotOutSvrReportDTO> retrieveIotCmnctSttsPopList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    public Integer retrieveIotCmnctSttsPopListCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동조회 */
    public List<TbIotOutSvrReportDTO> retrieveIotIntrnSvrCnctnList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    public Integer retrieveIotIntrnSvrCnctnListCount(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동중복체크 */
    public Integer duplicationChkIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동등록 */
    public void insertIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동수정-조회 */
    public List<TbIotOutSvrReportDTO> selectOneIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동수정-수정 */
    public void updateIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
    
    /* 내부서버연동삭제 */
    public void deleteIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO);
}
