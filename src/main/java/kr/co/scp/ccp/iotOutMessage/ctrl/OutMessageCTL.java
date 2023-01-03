package kr.co.scp.ccp.iotOutMessage.ctrl;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotOutMessage.dto.TbIotOutSvrMsgSetMDTO;
import kr.co.scp.ccp.iotOutMessage.svc.OutMessageSvrInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping(value = "/online/outsvrmsg")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class OutMessageCTL {

	@Autowired
	private OutMessageSvrInfoService outMessageSvrInfoService;
	
	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/outsvrmsglist", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrMsgList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exSvrMsgList = outMessageSvrInfoService.retrieveIotOutSvrMsgList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exSvrMsgList);
	}
	@RequestMapping(value = "/outsvrmsghlist", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrMsgHList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exSvrMsgHList = outMessageSvrInfoService.retrieveIotOutSvrMsgHList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exSvrMsgHList);
	}
	@RequestMapping(value = "/getSvcListBySeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveGetSvcListByseqList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exSvcList = outMessageSvrInfoService.retrieveGetSvcListByseqList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exSvcList);
	}
	@RequestMapping(value = "/getCustListInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotGetCustList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exCustList = outMessageSvrInfoService.retrieveIotGetCustList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exCustList);
	}
	@RequestMapping(value = "/getDevClsInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotGetDevClsList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exDevClsList = outMessageSvrInfoService.retrieveIotGetDevClsList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exDevClsList);
	}
	@RequestMapping(value = "/getDevMdlInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotGetDevMdlList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exDevMdlList = outMessageSvrInfoService.retrieveIotGetDevMdlList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exDevMdlList);
	}
	@RequestMapping(value = "/getOutMsgSendInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutMsgSendList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exOutMsgSendList = outMessageSvrInfoService.retrieveIotOutMsgSendList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exOutMsgSendList);
	}
	@RequestMapping(value = "/getOutMsgRecieveInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutMsgRecieveList(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		HashMap<String, Object> exOutMsgRecieveList = outMessageSvrInfoService.retrieveIotOutMsgRecieveList(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(exOutMsgRecieveList);
	}
	@RequestMapping(value = "/createoutsvrmsg", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTOutSvrM(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		outMessageSvrInfoService.insertTbIoTOutSvrMsgSet(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok();
	}
	@RequestMapping(value = "/updateoutsvrmsg", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateTbIoTOutSvrM(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		outMessageSvrInfoService.updateTbIoTOutSvrMsgSet(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok();
	}
	@RequestMapping(value = "/outsvrmsg", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrMsg(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		TbIotOutSvrMsgSetMDTO result = outMessageSvrInfoService.retrieveIotOutSvrMsg(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(result);
	}
	@RequestMapping(value = "/outsvrmsgh", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrMsgH(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		TbIotOutSvrMsgSetMDTO result = outMessageSvrInfoService.retrieveIotOutSvrMsgH(tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(result);
	}
	/**
	 * 엑셀 파일다운로드
	 * 
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return HttpServletResponse
	 * @throws BizException
	 */
	@RequestMapping(value = "/outsvrmsgExcelDown", method = RequestMethod.POST)
	public void excelDownload(HttpServletRequest request, HttpServletResponse response, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) {
		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "OUTMSG_ExcelTemplate" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){ 
			wb = outMessageSvrInfoService.excelCreate(tbIotOutSvrMsgSetMDTO);
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
	
	@RequestMapping(value = "/removeoutsvrmsg", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIoTOutSvrMsg(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		boolean result = outMessageSvrInfoService.deleteTbIoTOutSvrMsg(request,tbIotOutSvrMsgSetMDTO);
		return responseComUtil.setResponse200ok(result);
	}
	
	/**
	 * 고객사/서비스명/장비유형/장비모델/전문구분 중복조회 
	 * @param request
	 * @param tbIotOutSvrMsgSetMDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author lee.h.s
	 */
	@RequestMapping(value = "/retrieveDuplicatedAdd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCust(HttpServletRequest request, @RequestBody TbIotOutSvrMsgSetMDTO tbIotOutSvrMsgSetMDTO) throws BizException {
		
		int result = outMessageSvrInfoService.retrieveDuplicatedAdd(tbIotOutSvrMsgSetMDTO);
			
		
		return responseComUtil.setResponse200ok(result);
	}

}
