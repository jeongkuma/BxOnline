package kr.co.scp.ccp.login.ctl;

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
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDTO;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistDTO;
import kr.co.scp.ccp.login.svc.TbIotLoginHistSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotloginhist")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class TbIotLoginHistCTL {
	@Autowired
	private TbIotLoginHistSVC tbIotLoginHistSVC;

	@Autowired
	private ResponseComUtil responseComUtil;
	/**
	 * 로그인
	 *
	 * @author jkuma
	 * @param request
	 * @param
	 * @return
	 * @throws BizException
	 */
	// 로그인 이력 조회
	@RequestMapping(value = "/retrieveIotLoginHist", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveLoginHist(HttpServletRequest request, @RequestBody TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException {
		HashMap<String, Object> loginHistMap = tbIotLoginHistSVC.retrieveTbIotLoginHist(tbIoTLoginHistDTO);
		return responseComUtil.setResponse200ok(loginHistMap);
	}

	/**
	 * 엑셀 파일다운로드
	 *
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/downloadIotLoginHist", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public void downloadIotCtrlHist(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIoTLoginHistDTO tbIoTLoginHistDTO) {
		XSSFWorkbook wb = null;

		String fileName = "loginHist" + "_" + new Random().nextInt(1000) + ".xlsx";

		try {
			wb = tbIotLoginHistSVC.excelCreate(tbIoTLoginHistDTO);
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