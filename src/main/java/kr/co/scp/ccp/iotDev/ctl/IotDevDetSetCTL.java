package kr.co.scp.ccp.iotDev.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevDetSetSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevDetSetCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotDevDetSetSVC iotDevDetSetSVC;

	
	/**
	 * 장비 속성Set 조회 
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotDevDetSet", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotdevDetSet(HttpServletRequest request, @RequestBody TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		List<TbIotDevDetSetDTO> tbIotDevDetSetDTORn = iotDevDetSetSVC.retrieveIotDevDetSet(tbIotDevDetSetDTO);
		
		return responseComUtil.setResponse200ok(tbIotDevDetSetDTORn);
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
	@RequestMapping(value = "/createIotPasteDevDetSetTemp", method = RequestMethod.POST)
	public void createIotPasteDevDetSetTemp(HttpServletRequest request, HttpServletResponse response) {
		// 로그인한 사람의 정보를 받아와서 엑셀에 넣어줘야 하는 내용
		// 로그인한 사용자 정보 : 
		// 2. 속한 조직의 조직명들		
		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "이상징후 SET 템플릿" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){ 
			wb = iotDevDetSetSVC.createIotPasteDevDetSetTemp();
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
	@RequestMapping(value = "/createIotDevDetSetAll", method = RequestMethod.POST)
	public ComResponseDto<?> createIotDevDetSetAll(HttpServletRequest request)  throws BizException{
		iotDevDetSetSVC.createIotDevDetSetAll(request);
//	
		return responseComUtil.setResponse200ok();
	}
	
	
}	
			
	
