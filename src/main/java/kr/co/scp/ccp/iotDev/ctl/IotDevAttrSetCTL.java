package kr.co.scp.ccp.iotDev.ctl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSetSVC;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevAttrSetCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotDevAttrSetSVC iotDevAttrSetSVC;

	
	/**
	 * 장비 속성Set 조회 
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotDevAttrSet", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotdevAttrSet(HttpServletRequest request, @RequestBody TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		List<TbIotDevAttrSetDTO> tbIotDevAttrSetDTORn = iotDevAttrSetSVC.retrieveIotDevAttrSet(tbIotDevAttrSetDTO);
		
		return responseComUtil.setResponse200ok(tbIotDevAttrSetDTORn);
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
	@RequestMapping(value = "/createIotPasteDevAttrSetTemp", method = RequestMethod.POST)
	public void createIotPasteDevAttrSetTemp(HttpServletRequest request, HttpServletResponse response) {
		// 로그인한 사람의 정보를 받아와서 엑셀에 넣어줘야 하는 내용
		// 로그인한 사용자 정보 : 
		// 2. 속한 조직의 조직명들		
		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "장비 속성 SET 템플릿" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){ 
			wb = iotDevAttrSetSVC.createIotPasteDevAttrSetTemp();
			String header = request.getHeader("User-Agent");
			fileName = ExcelUtils.urlDecodeFileName(header, fileName);
			ExcelUtils.addFileDownloadHeader(response, fileName);
			wb.write(sos);

		} catch (Exception e) {
			ExcelUtils.addErrorFileDownloadHeader(response);
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
	@RequestMapping(value = "/createIotDevAttrSetAll", method = RequestMethod.POST)
	public ComResponseDto<?> createIotDevAttrSetAll(HttpServletRequest request)  throws BizException{
		iotDevAttrSetSVC.createIotDevAttrSetAll(request);
//	
		return responseComUtil.setResponse200ok();
	}
	
	
}	
					
	
