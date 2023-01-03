package kr.co.scp.ccp.iotOutSvrReport.ctrl;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotOutSvrReport.dto.TbIotOutSvrReportDTO;
import kr.co.scp.ccp.iotOutSvrReport.svc.OutSvrReportService;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotoutsvrreport")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class OutSvrReportCTL {

	@Autowired
	private OutSvrReportService outSvrReportService;

	@Autowired
	private ResponseComUtil responseComUtil;

	/* 고객사리스트 */
	@RequestMapping(value = "/custNmList", method = RequestMethod.POST)
	public ComResponseDto<?> custNmList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== custNmList === ");
		HashMap<String, Object> custNmList = outSvrReportService.custNmList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(custNmList);
	}
	
	/* 서비스명리스트 */
	@RequestMapping(value = "/svcNmList", method = RequestMethod.POST)
	public ComResponseDto<?> svcNmList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== svcNmList === ");
		HashMap<String, Object> svcNmList = outSvrReportService.svcNmList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(svcNmList);
	}
	
	/* 장비유형리스트 */
	@RequestMapping(value = "/devClsList", method = RequestMethod.POST)
	public ComResponseDto<?> devClsList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== devClsList === ");
		HashMap<String, Object> devClsList = outSvrReportService.devClsList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(devClsList);
	}
	
	/* 장비모델리스트 */
	@RequestMapping(value = "/devMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> devMdlList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== devMdlList === ");
		HashMap<String, Object> devMdlList = outSvrReportService.devMdlList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(devMdlList);
	}
	
	/* 가입장비리스트 */
	@RequestMapping(value = "/entrDevList", method = RequestMethod.POST)
	public ComResponseDto<?> entrDevList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== entrDevList === ");
		HashMap<String, Object> entrDevList = outSvrReportService.entrDevList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(entrDevList);
	}
	
	/* 외부서버리스트 */
	@RequestMapping(value = "/outSvrList", method = RequestMethod.POST)
	public ComResponseDto<?> outSvrList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== outSvrList === ");
		HashMap<String, Object> outSvrList = outSvrReportService.outSvrList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrList);
	}
	
	/* 외부서버송수신이력조회 */
	@RequestMapping(value = "/outSvrRtReportList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtReportList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtReportList === ");
		HashMap<String, Object> outSvrRtReportList = outSvrReportService.retrieveIotOutSvrRtReportList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtReportList);
	}
	
	/* 외부서버송수신현황 - 재전송 */
	@RequestMapping(value = "/outSvrReSend", method = RequestMethod.POST)
	public ComResponseDto<?> outSvrReSend(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== reSend === ");
		Object successYn = outSvrReportService.outSvrReSend(tbIotOutSvrReportDTO);
		
		return responseComUtil.setResponse200ok(successYn);
	}

	/**
	 * 엑셀 파일다운로드
	 * 
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/excelDownload", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public void excelDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
		
		XSSFWorkbook wb = null;
		String fileName = null;
		String excelFlag = tbIotOutSvrReportDTO.getExcelFlag();

		//외부서버 송수신 현황
		if(excelFlag.equals("outSvrRt")) {
			fileName = "outSvrRtReportList" + "_" + new Random().nextInt(1000) + ".xlsx";
		//외부서버 송수신 현황 상세내역
		} else if(excelFlag.equals("outSvrRtDtl")) {
			fileName = "outSvrRtDetailReportList" + "_" + new Random().nextInt(1000) + ".xlsx";
		//모델별 송수신 현황
		} else if(excelFlag.equals("outSvrRtMdl")) {
			fileName = "outSvrRtMdlReportList" + "_" + new Random().nextInt(1000) + ".xlsx";
		//가동률
		} else if(excelFlag.equals("outSvrRtOprtn")) {			
			fileName = "outSvrRtOprtnReportList" + "_" + new Random().nextInt(1000) + ".xlsx";
		//통신현황
		} else if(excelFlag.equals("cmnctStts")) {			
			fileName = "cmnctSttsList" + "_" + new Random().nextInt(1000) + ".xlsx";
		}
			
		try {
			wb = outSvrReportService.excelDownload(tbIotOutSvrReportDTO);
			String header = request.getHeader("User-Agent");
			fileName = ExcelUtils.urlDecodeFileName(header, fileName);
			ExcelUtils.addFileDownloadHeader(response, fileName);
			wb.write(response.getOutputStream());

		} catch (Exception e) {
			ExcelUtils.addErrorFileDownloadHeader(response);
			log.error(e.getMessage());	
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ignore) { 
			}
		}
	}
	
	/* 외부서버송수신이력조회-전문상세내역 */
	@RequestMapping(value = "/outSvrRtReportDtlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtReportDtlList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtReportDtlList === ");
		TbIotOutSvrReportDTO outSvrRtReportDtail = outSvrReportService.retrieveIotOutSvrRtReportDtlList(TbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtReportDtail);
	}
	
	/* 외부서버송수신이력조회 - 팝업*/
	@RequestMapping(value = "/outSvrRtReportListPop", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtReportListPop(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtReportListPop === ");
		HashMap<String, Object> outSvrRtReportListPop = outSvrReportService.retrieveIotOutSvrRtReportListPop(TbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtReportListPop);
	}

	/* 외부서버송수신이력조회 - 팝업 - 송수신전문내역*/
	@RequestMapping(value = "/outSvrRtReportListPopDtl", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtReportListPopDtl(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtReportListPopDtl === ");
		TbIotOutSvrReportDTO outSvrRtReportListPopDtl = outSvrReportService.retrieveIotOutSvrRtReportListPopDtl(TbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtReportListPopDtl);
	}

	/* 모델별전송현황 */
	@RequestMapping(value = "/outSvrRtMdlReportList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtMdlReportList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtMdlReportList === ");
		HashMap<String, Object> outSvrRtMdlReportList = outSvrReportService.retrieveIotOutSvrRtMdlReportList(TbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtMdlReportList);
	}
	
	/* 가동률 */
	@RequestMapping(value = "/outSvrRtOprtnRate", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrRtOprtnRateList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO TbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotOutSvrRtOprtnRateList === ");
		HashMap<String, Object> outSvrRtOprtnRateList = outSvrReportService.retrieveIotOutSvrRtOprtnRate(TbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(outSvrRtOprtnRateList);
	}
	
	/* 통신현황 - 고객사리스트 */
	@RequestMapping(value = "/cmnctSttsCustNmList", method = RequestMethod.POST)
	public ComResponseDto<?> cmnctSttsCustNmList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== cmnctSttsCustNmList === ");
		HashMap<String, Object> cmnctSttsCustNmList = outSvrReportService.cmnctSttsCustNmList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsCustNmList);
	}
	
	/* 통신현황 - 서비스명리스트 */
	@RequestMapping(value = "/cmnctSttsSvcNmList", method = RequestMethod.POST)
	public ComResponseDto<?> cmnctSttsSvcNmList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== cmnctSttsSvcNmList === ");
		HashMap<String, Object> cmnctSttsSvcNmList = outSvrReportService.cmnctSttsSvcNmList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsSvcNmList);
	}
	
	/* 통신현황 - 외부서버명리스트 */
	@RequestMapping(value = "/cmnctSttsOutSvrList", method = RequestMethod.POST)
	public ComResponseDto<?> cmnctSttsOutSvrList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== cmnctSttsOutSvrList === ");
		HashMap<String, Object> cmnctSttsOutSvrList = outSvrReportService.cmnctSttsOutSvrList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsOutSvrList);
	}
	
	/* 통신현황 - 장비유형리스트 */
	@RequestMapping(value = "/cmnctSttsDevClsList", method = RequestMethod.POST)
	public ComResponseDto<?> cmnctSttsDevClsList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== cmnctSttsDevClsList === ");
		HashMap<String, Object> cmnctSttsDevClsList = outSvrReportService.cmnctSttsDevClsList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsDevClsList);
	}
	
	/* 통신현황 - 장비모델리스트 */
	@RequestMapping(value = "/cmnctSttsDevMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> cmnctSttsDevMdlList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== cmnctSttsDevMdlList === ");
		HashMap<String, Object> cmnctSttsDevMdlList = outSvrReportService.cmnctSttsDevMdlList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsDevMdlList);
	}
	
	/* 통신현황조회 */
	@RequestMapping(value = "/cmnctSttsList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmnctSttsList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotCmnctSttsList === ");
		HashMap<String, Object> cmnctSttsList = outSvrReportService.retrieveIotCmnctSttsList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsList);
	}
	
	/* 통신현황상세조회 */
	@RequestMapping(value = "/cmnctSttsPopList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmnctSttsPopList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotCmnctSttsPopList === ");
		HashMap<String, Object> cmnctSttsPopList = outSvrReportService.retrieveIotCmnctSttsPopList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(cmnctSttsPopList);
	}
	
	/* 내부서버연동조회 */
	@RequestMapping(value = "/intrnSvrCnctnList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotIntrnSvrCnctnList(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== retrieveIotIntrnSvrCnctnList === ");
		HashMap<String, Object> intrnSvrCnctnList = outSvrReportService.retrieveIotIntrnSvrCnctnList(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(intrnSvrCnctnList);
	}
	
	/* 내부서버연동등록 */
	@RequestMapping(value = "/addIotIntrnSvrCnctn", method = RequestMethod.POST)
	public ComResponseDto<?> insertIotIntrnSvrCnctn(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== insertIotIntrnSvrCnctn === ");
		HashMap<String, Object> result = outSvrReportService.insertIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(result);
	}
	
	/* 내부서버연동수정-조회 */
	@RequestMapping(value = "/selectOneIotIntrnSvrCnctn", method = RequestMethod.POST)
	public ComResponseDto<?> selectOneIotIntrnSvrCnctn(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== insertIotIntrnSvrCnctn === ");
		HashMap<String, Object> selectOneIotIntrnSvrCnctn = outSvrReportService.selectOneIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(selectOneIotIntrnSvrCnctn);
	}
	
	/* 내부서버연동수정-수정 */
	@RequestMapping(value = "/updateIotIntrnSvrCnctn", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotIntrnSvrCnctn(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== updateIotIntrnSvrCnctn === ");
		HashMap<String, Object> result = outSvrReportService.updateIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok(result);
	}
	
	/* 내부서버연동삭제 */
	@RequestMapping(value = "/deleteIotIntrnSvrCnctn", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotIntrnSvrCnctn(HttpServletRequest request, @RequestBody TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws BizException {
		log.debug("=== deleteIotIntrnSvrCnctn === ");
		outSvrReportService.deleteIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/* 내부서버연동동기화 */
	@RequestMapping(value = "/syncIntrnSvrCnctn", method = RequestMethod.POST)
	public ComResponseDto<?> syncIntrnSvrCnctn(HttpServletRequest request) throws BizException {
		log.debug("=== syncIntrnSvrCnctn === ");
		ComRequestDto comRequestDto = ReqContextComponent.getComRequestDto();
		outSvrReportService.syncIntrnSvrCnctn(comRequestDto);
		return responseComUtil.setResponse200ok();
	}
}
