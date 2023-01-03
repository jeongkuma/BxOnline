package kr.co.scp.ccp.iotDev.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevAttrCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotDevAttrSVC iotDevAttrSVC;

	/**
	 * 장비 속성 조회
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotDevAttr", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDevAttr(HttpServletRequest request, @RequestBody TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotDevAttrDTO> tbIotDevAttrDTORn = iotDevAttrSVC.retrieveIotDevAttr(tbIotDevAttrDTO);

		return responseComUtil.setResponse200ok(tbIotDevAttrDTORn);
	}

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
	@RequestMapping(value = "/createIotPasteDevAttrTemp", method = RequestMethod.POST)
	public void createIotPasteDevAttrTemp(HttpServletRequest request, HttpServletResponse response) {

		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "장비 속성 템플릿" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){
			wb = iotDevAttrSVC.createIotPasteDevAttrTemp();
			
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



	/**
	 * 사용자 일괄 등록
	 * @param request
	 * @param tbIotUseDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/createIotDevAttrAll", method = RequestMethod.POST)
	public ComResponseDto<?> createIotDevAttrAll(HttpServletRequest request)  throws BizException{

		iotDevAttrSVC.createIotDevAttrAll(request);

		return responseComUtil.setResponse200ok();
	}


}
