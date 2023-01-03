package kr.co.scp.ccp.iotCtrlHist.ctl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
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
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistConditionReqDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
import kr.co.scp.ccp.iotCtrlHist.svc.IotCtrlHistSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotctrlHist")
public class IotCtrlHistCTL {
	
	@Autowired
	private IotCtrlHistSVC iotCtrlHistSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	/**
	 * 제어이력 조회
	 * @param request
	 * @param TbIotCtrlHistDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotCtrlHistList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCtrlHist(HttpServletRequest request, @RequestBody TbIotCtrlHistDTO tbIotCtrlHistDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> TbIotCtrlHistList = iotCtrlHistSVC.retrieveIotCtrlHist(tbIotCtrlHistDTO);
		
		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}
	
	/**
	 * 장비유형,제어종류, 제어상태 목록 조회
	 * @param request
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotCtrAllcondition", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCtrAllcondition(HttpServletRequest request, @RequestBody TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO ) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> TbIotCtrlHistList = iotCtrlHistSVC.retrieveIotCtrAllcondition(tbIotCtrlHistConditionReqDTO);
		
		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}  
	
	/**
	 * 유형에 맞는 장비 모델명 목록 조회
	 * @param request
	 * @param TbIotDevMdlDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveDevMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDevMdlList(HttpServletRequest request, @RequestBody TbIotDevMdlReqDTO tbIotDevMdlReqDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		List<TbIotDevMdlDTO> TbIotCtrlHistList = iotCtrlHistSVC.retrieveDevMdlList(tbIotDevMdlReqDTO);
		
		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}
	
	/**
	 * 엑셀 파일다운로드
	 * 
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/downloadIotCtrlHist", method = RequestMethod.POST)
	public void downloadIotCtrlHist(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotCtrlHistDTO tbIotCtrlHistDTO) {
		XSSFWorkbook wb = null;

		String fileName = "제어이력 " + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
			// TbIoTNotiBrdDTO dto = new TbIoTNotiBrdDTO();

			wb = iotCtrlHistSVC.excelCreate(tbIotCtrlHistDTO);

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
	
	/**
	 * 제어예약 삭제 
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotCtrlList", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotCtrlList(HttpServletRequest request, @RequestBody TbIotCtrlHistDTO tbIotCtrlHistDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		iotCtrlHistSVC.deleteIotCtrlList(tbIotCtrlHistDTO);

		return responseComUtil.setResponse200ok(tbIotCtrlHistDTO);
	}
}
