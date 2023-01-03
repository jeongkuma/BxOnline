package kr.co.scp.ccp.iotStatReport.ctrl;

import java.util.Map;
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
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotStatReport.dto.TbIotComCollStatDTO;
import kr.co.scp.ccp.iotStatReport.svc.IotStatReportSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotstatreport")
public class IotStatReportCTL {

	@Autowired
	private IotStatReportSVC iotStatReportSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/retrieveIotStatReportList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotStatReportList(HttpServletRequest request, @RequestBody TbIotComCollStatDTO tbIotComCollStatDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//		Map<String, Object> reportData = iotStatReportSVC.retrieveIotStatReportList(tbIotComCollStatDTO);
		Map<String, Object> reportData = iotStatReportSVC.retrieveIotStatReportListNew(tbIotComCollStatDTO);

		return responseComUtil.setResponse200ok(reportData);
	}

	/**
	 * 엑셀 파일다운로드
	 *
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/downloadIotStatReportList", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public void downloadIotStatReportList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotComCollStatDTO tbIotComCollStatDTO) {
		XSSFWorkbook wb = null;

		String fileName = "statReportList" + "_" + new Random().nextInt(1000) + ".xlsx";

		try {
			wb = iotStatReportSVC.downloadIotStatReportList(tbIotComCollStatDTO);
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
