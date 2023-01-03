package kr.co.scp.ccp.iotEntrDevStat.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotEntrDevStat.dto.TbIotEntrDevStatDTO;
import kr.co.scp.ccp.iotEntrDevStat.svc.IotEntrDevStatSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RestController
@RequestMapping(value = "/online/iotentrdevstat")
public class IotEntrDevStatCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotEntrDevStatSVC iotEntrDevStatSVC;


	/**
	 * 시간별 통계  조회
	 * @param request
	 * @param TbIotEntrDevStatDTO
	 * @return List<TbIotEntrDevStatDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveHourStatList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveHourStatList(HttpServletRequest request, @RequestBody TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveHourStatList(tbIotEntrDevStatDTO);
		return responseComUtil.setResponse200ok(TbIotEntrDevStatDTORn);
	}

	/**
	 * 일별 통계  조회
	 * @param request
	 * @param TbIotEntrDevStatDTO
	 * @return List<TbIotEntrDevStatDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveDayStatList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDayStatList(HttpServletRequest request, @RequestBody TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//		List<TbIotEntrDevStatDTO> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveDayStatList(tbIotEntrDevStatDTO);
		HashMap<String, Object> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveDayStatList(tbIotEntrDevStatDTO);
		return responseComUtil.setResponse200ok(TbIotEntrDevStatDTORn);
	}

	/**
	 * 주별 통계  조회
	 * @param request
	 * @param TbIotEntrDevStatDTO
	 * @return List<TbIotEntrDevStatDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveWeekStatList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveWeekStatList(HttpServletRequest request, @RequestBody TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveWeekStatList(tbIotEntrDevStatDTO);
		return responseComUtil.setResponse200ok(TbIotEntrDevStatDTORn);
	}

	/**
	 * 월별 통계  조회
	 * @param request
	 * @param TbIotEntrDevStatDTO
	 * @return List<TbIotEntrDevStatDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveMonthStatList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveMonthStatList(HttpServletRequest request, @RequestBody TbIotEntrDevStatDTO tbIotEntrDevStatDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//		List<TbIotEntrDevStatDTO> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveMonthStatList(tbIotEntrDevStatDTO);
		HashMap<String, Object> TbIotEntrDevStatDTORn = iotEntrDevStatSVC.retrieveMonthStatList(tbIotEntrDevStatDTO);
		return responseComUtil.setResponse200ok(TbIotEntrDevStatDTORn);
	}

	/**
	 * 시간별 통계 엑셀다운로드
	 * @param request
	 * @param TbIotEntrDevStatDTO
	 * @return List<TbIotEntrDevStatDTO>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveStatExcelDownload", method = RequestMethod.POST)
	public void retrieveStatExcelDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TbIotEntrDevStatDTO tbIotEntrDevStatDTO) {
		XSSFWorkbook wb = null;

		String fileName = "stat" + "_" + new Random().nextInt(1000) + ".xlsx";

		System.out.println("##########" + fileName);

		try {
			wb = iotEntrDevStatSVC.retrieveStatExcelDownload(tbIotEntrDevStatDTO);

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

