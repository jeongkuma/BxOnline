package kr.co.scp.ccp.iotEntrDevCol.ctrl;

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

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotEntrDevCol.dto.TbIotEDevColValDTO;
import kr.co.scp.ccp.iotEntrDevCol.dto.TbIotEntrDevColReqListDTO;
import kr.co.scp.ccp.iotEntrDevCol.svc.IotEntrDevColSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotentrdevcol")
public class IotEntrDevColCTL {

	@Autowired
	private IotEntrDevColSVC iotEntrDevColSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	/**
	 * 수집 이력 목록 조회
	 * @param request
	 * @param TbIotDevMdlReqDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEntrDevColList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEntrDevColList(HttpServletRequest request, @RequestBody TbIotEDevColValDTO tbIotEDevColValDTO) throws BizException {
		
		HashMap<String, Object> TbIotEntrDevColList = iotEntrDevColSVC.retrieveEntrDevColList(tbIotEDevColValDTO);
		// List<TbIotEntrDevColDTO> TbIotEntrDevColList = iotEntrDevColSVC.retrieveEntrDevColList(tbIotEntrDevColReqListDTO);
		
		return responseComUtil.setResponse200ok(TbIotEntrDevColList);
	}
	
	/**
	 * 엑셀 다운로드
	 * @param request
	 * @param TbIotDevMdlReqDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/downloadEntrDevColList", method = RequestMethod.POST)
	public void downloadEntrDevColList(HttpServletRequest request, HttpServletResponse response
			, @RequestBody TbIotEDevColValDTO tbIotEDevColValDTO) throws BizException {
		
		XSSFWorkbook wb = null;
		
		String fileName = "entrDevColVal" + "_" + new Random().nextInt(1000) + ".xlsx";
		
		try {

			wb = iotEntrDevColSVC.downloadEntrDevColList(tbIotEDevColValDTO);

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
