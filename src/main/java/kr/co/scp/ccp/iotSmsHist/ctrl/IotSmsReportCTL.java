package kr.co.scp.ccp.iotSmsHist.ctrl;

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
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;
import kr.co.scp.ccp.iotSmsHist.svc.IotSmsReportSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotSmsReport")
public class IotSmsReportCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotSmsReportSVC IotSmsReportSVC;
	
	/**
	 * SMS 발송 이력
	 * @param request
	 * @param TbIotSmsTranHistDTO
	 * @return List<TbIotSmsTranHistDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotSmsReportList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotSmsReportList(HttpServletRequest request, @RequestBody TbIotSmsTranHistDTO tbIotSmsTranHistDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> tbIotSmsTranHist = IotSmsReportSVC.retrieveIotSmsReportList(tbIotSmsTranHistDTO);
		
		return responseComUtil.setResponse200ok(tbIotSmsTranHist);
	}
	
	@RequestMapping(value = "/downloadIotSmsReport", method = RequestMethod.POST)
	public void downloadIotSmsReport(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotSmsTranHistDTO tbIotSmsTranHistDTO) {
		XSSFWorkbook wb = null;

		String fileName = "smsReport" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
		
			wb = IotSmsReportSVC.excelCreate(tbIotSmsTranHistDTO);
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
	
}
