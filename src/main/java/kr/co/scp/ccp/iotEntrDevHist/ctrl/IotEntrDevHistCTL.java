package kr.co.scp.ccp.iotEntrDevHist.ctrl;

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

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqListDTO;
import kr.co.scp.ccp.iotEntrDevHist.svc.IotEntrDevHistSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotentrdevhist")
public class IotEntrDevHistCTL {

	@Autowired
	private IotEntrDevHistSVC iotEntrDevHistSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 템플릿 헤더 조회
	 * @param request
	 * @param TbIotEntrDevHistDTO
	 * @return
	 * @throws BizException
	 */

	/*@RequestMapping(value = "/retrieveIotTempHeaderList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotTempHeaderList(HttpServletRequest request, @RequestBody TbIotEntrDevHistDTO tbIotEntrDevHistDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotCtrlHistList = iotEntrDevHistSVC.retrieveIotTempHeaderList(tbIotEntrDevHistDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}*/

	/**
	 * 장비유형 목록 조회
	 * @param request
	 * @param TbIotEntrDevHistDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveDevClsList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDevClsList(HttpServletRequest request, @RequestBody TbIotEntrDevHistReqDTO tbIotEntrDevHistReqDTO) throws BizException {

		List<TbIotDevMdlDTO> TbIotCtrlHistList = iotEntrDevHistSVC.retrieveDevClsList(tbIotEntrDevHistReqDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}

	/**
	 * 장비 모델 목록 조회
	 * @param request
	 * @param TbIotDevMdlDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveDevMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDevMdlList(HttpServletRequest request, @RequestBody TbIotDevMdlReqDTO tbIotDevMdlReqDTO) throws BizException {

		List<TbIotDevMdlDTO> TbIotCtrlHistList = iotEntrDevHistSVC.retrieveDevMdlList(tbIotDevMdlReqDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlHistList);
	}

	/**
	 * 수집 이력 목록 조회
	 * @param request
	 * @param TbIotDevMdlReqDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveEntrDevHistList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEntrDevHistList(HttpServletRequest request, @RequestBody TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) throws BizException {

		HashMap<String, Object> TbIotEntrDevHistList = iotEntrDevHistSVC.retrieveEntrDevHistList(tbIotEntrDevHistReqListDTO);
		// List<TbIotEntrDevHistDTO> TbIotEntrDevHistList = iotEntrDevHistSVC.retrieveEntrDevHistList(tbIotEntrDevHistReqListDTO);

		return responseComUtil.setResponse200ok(TbIotEntrDevHistList);
	}

	/**
	 * 엑셀 다운로드
	 * @param request
	 * @param TbIotDevMdlReqDTO
	 * @return
	 * @throws BizException
	 */
//	@RequestMapping(value = "/downloadEntrDevHistList", method = RequestMethod.POST)
//	public void downloadEntrDevHistList(HttpServletRequest request, HttpServletResponse response
//			, @RequestBody TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) throws BizException {
//		iotEntrDevHistSVC.csvCreate(tbIotEntrDevHistReqListDTO, response);
//	}

	@RequestMapping(value = "/downloadEntrDevHistList", method = RequestMethod.POST)
	public void downloadEntrDevHistList(HttpServletRequest request, HttpServletResponse response
			, @RequestBody TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) throws BizException {


		XSSFWorkbook wb = null;

		String fileName = "entrDevHist" + "_" + new Random().nextInt(1000) + ".xlsx";

		try {
			// TbIoTNotiBrdDTO dto = new TbIoTNotiBrdDTO();

			wb = iotEntrDevHistSVC.excelCreate(tbIotEntrDevHistReqListDTO);

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
