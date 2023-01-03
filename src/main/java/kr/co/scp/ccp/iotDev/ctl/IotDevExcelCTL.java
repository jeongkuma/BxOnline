package kr.co.scp.ccp.iotDev.ctl;


import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevExcelSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Slf4j
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevExcelCTL {

	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	IotDevExcelSVC iotDevExcelSVC;
	
	/**
	 * 장비 고객 할당
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/createIotPasteDev", method = RequestMethod.POST)
	public void createIotPasteDev(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) {
		XSSFWorkbook wb = null;

		String fileName = "DevtoCust" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {

			wb = iotDevExcelSVC.createIotPasteDev(tbIotDevExcelDTO);
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
	 * 장비 속성 고객 할당
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/createIotPasteDevAtb", method = RequestMethod.POST)
	public void createIotPasteDevAtb(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) {
		XSSFWorkbook wb = null;

		String fileName = "DevAtbtoCust" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
			wb = iotDevExcelSVC.createIotPasteDevAtb(tbIotDevExcelDTO);
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
	 * 장비 속성 값 고객 할당
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/createIotPasteDevAtbSet", method = RequestMethod.POST)
	public void createIotPasteDevAtbSet(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) {
		XSSFWorkbook wb = null;

		String fileName = "DevAtbSettoCust" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
			wb = iotDevExcelSVC.createIotPasteDevAtbSet(tbIotDevExcelDTO);
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
	 * 장비 이상징후 고객 할당
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/createIotPasteDevDetSet", method = RequestMethod.POST)
	public void createIotPasteDevDetSet(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) {
		XSSFWorkbook wb = null;

		String fileName = "DevDetSettoCust" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
			wb = iotDevExcelSVC.createIotPasteDevDetSet(tbIotDevExcelDTO);
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

//		return response;
	}
	
	/**
	 * 고객 번호 조회
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveCustSeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveCustSeq(HttpServletRequest request, @RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		int tbIotExcelDTORn = iotDevExcelSVC.retrieveCustSeq(tbIotDevExcelDTO);
		return responseComUtil.setResponse200ok(tbIotExcelDTORn);
	}
	
	/**
	 * 장비 속성 엑셀 다운로드
	 * @param request
	 * @param tbIotDevExcelDTO
	 * @return List<tbIotDevExcelDTO>
	 * @throws BizException
	 */
	/*
	@RequestMapping(value = "/createIotPasteDevAttr", method = RequestMethod.POST)
	public void createIotPasteDevAttr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotDevExcelDTO tbIotDevExcelDTO) {
		XSSFWorkbook wb = null;

		String fileName = "DevAttr" + "_" + new Random().nextInt(1000) + ".xlsx";

	

		try {
			wb = iotDevExcelSVC.createIotPasteDevAttr(tbIotDevExcelDTO);
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

//		return response;
	} 
	*/
	
/**
 * 엑셀 파일다운로드
 * 
 * @param request
 * @param response
 * @param comRequestDto
 * @return HttpServletResponse
 * @throws BizException
 * @author joseph
 */
@RequestMapping(value = "/createIotPasteDevTemp", method = RequestMethod.POST)
public void createIotPasteDevTemp(HttpServletRequest request, HttpServletResponse response) {
	// 로그인한 사람의 정보를 받아와서 엑셀에 넣어줘야 하는 내용
	// 로그인한 사용자 정보 : 
	// 2. 속한 조직의 조직명들		
	XSSFWorkbook wb = null;
	String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	String fileName = "일괄등록" + "_" + today + ".xlsx";
	try(ServletOutputStream sos = response.getOutputStream()){ 
		wb = iotDevExcelSVC.createIotPasteDevTemp();
		String header = request.getHeader("User-Agent");
		fileName = ExcelUtils.urlDecodeFileName(header, fileName);
		ExcelUtils.addFileDownloadHeader(response, fileName);
		wb.write(sos);

	} catch (Exception e) {
		ExcelUtils.addErrorFileDownloadHeader(response);
		throw new BizException(e, "excellDownloadError");
	} finally {
		try {
			if (wb != null) {
				wb.close();
			}
		} catch (Exception ignore) { 
		}
	}
}
}

