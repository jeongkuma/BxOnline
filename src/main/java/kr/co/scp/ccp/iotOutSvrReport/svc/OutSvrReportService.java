package kr.co.scp.ccp.iotOutSvrReport.svc;


import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotOutSvrReport.dto.TbIotOutSvrReportDTO;

public interface OutSvrReportService {
	
	/* 고객사리스트 */
	public HashMap<String, Object> custNmList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 서비스명리스트 */
	public HashMap<String, Object> svcNmList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 장비유형리스트 */
	public HashMap<String, Object> devClsList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 장비모델리스트 */
	public HashMap<String, Object> devMdlList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 가입장비리스트 */
	public HashMap<String, Object> entrDevList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 외부서버리스트 */
	public HashMap<String, Object> outSvrList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;

	/* 외부서버송수신이력재전송 */
    public String outSvrReSend(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException;
    
    /* 외부서버송수신이력조회 */
    public HashMap<String, Object> retrieveIotOutSvrRtReportList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 엑셀 다운로드 */
    public XSSFWorkbook excelDownload(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws Exception;
    
    /* 외부서버송수신현황-전문상세내역 */
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportDtlList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;

    /* 외부서버송수신이력조회 - 팝업*/
    public HashMap<String, Object> retrieveIotOutSvrRtReportListPop(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;

    /* 외부서버송수신이력조회 - 팝업 - 송수신전문내역*/
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportListPopDtl(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 모델별전송현황 */
    public HashMap<String, Object> retrieveIotOutSvrRtMdlReportList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 가동률 */
    public HashMap<String, Object> retrieveIotOutSvrRtOprtnRate(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;

    /* 통신현황 - 고객사리스트 */
	public HashMap<String, Object> cmnctSttsCustNmList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 통신현황 - 서비스명리스트 */
	public HashMap<String, Object> cmnctSttsSvcNmList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 통신현황 - 외부서버명리스트 */
	public HashMap<String, Object> cmnctSttsOutSvrList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 통신현황 - 장비유형리스트 */
	public HashMap<String, Object> cmnctSttsDevClsList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 통신현황 - 장비모델리스트 */
	public HashMap<String, Object> cmnctSttsDevMdlList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
	
	/* 통신현황조회 */
    public HashMap<String, Object> retrieveIotCmnctSttsList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 통신현황상세조회 */
    public HashMap<String, Object> retrieveIotCmnctSttsPopList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동조회 */
    public HashMap<String, Object> retrieveIotIntrnSvrCnctnList(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동등록 */
    public HashMap<String, Object> insertIotIntrnSvrCnctn(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동수정-조회 */
    public HashMap<String, Object> selectOneIotIntrnSvrCnctn(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동수정-수정 */
    public HashMap<String, Object> updateIotIntrnSvrCnctn(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동삭제 */
    public void deleteIotIntrnSvrCnctn(TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException;
    
    /* 내부서버연동동기화 */
    public void syncIntrnSvrCnctn(ComRequestDto comRequestDto) throws BizException;
	
}
