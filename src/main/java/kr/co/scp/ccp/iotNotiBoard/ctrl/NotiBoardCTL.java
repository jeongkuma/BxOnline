package kr.co.scp.ccp.iotNotiBoard.ctrl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDetailDTO;
import kr.co.scp.ccp.iotNotiBoard.svc.NotiBoardSVC;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/online/iotnotiboard")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class NotiBoardCTL {

	@Autowired
	private NotiBoardSVC notiBoardSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Value("${file.upload-dir-noti}")
	private String FILE_UPLOAD_PATH;

	/**
	 * @Author jbs
	 * @param request
	 * @param notiBoardDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotNotiBoardList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveNotiBoardList(HttpServletRequest request, @RequestBody TbIoTNotiBrdDTO notiBoardDto) throws BizException {
		HashMap<String, Object> retrieveNotiBoardList = notiBoardSvc.retrieveNotiBoardList(notiBoardDto);
		return responseComUtil.setResponse200ok(retrieveNotiBoardList);
	}

	@RequestMapping(value = "/retrieveIotNotiBoardDetail", method = RequestMethod.POST)
	public ComResponseDto<?> getNotiBoardDetail(HttpServletRequest request, @RequestBody TbIoTNotiBrdDTO notiBoardDto) throws BizException {
		TbIoTNotiBrdDetailDTO tbIoTNotiBrdDetailDTO= notiBoardSvc.retrieveNotiBoardDetail(notiBoardDto);
		return responseComUtil.setResponse200ok(tbIoTNotiBrdDetailDTO);
	}

	@RequestMapping(value = "/createIotNotiBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTNotiBrd(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		TbIoTNotiBrdDTO notiBoardDto;
		try {
			notiBoardDto = (TbIoTNotiBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTNotiBrdDTO.class);
			notiBoardSvc.insertTbIoTNotiBrd(request, notiBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/updateIotNotiBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateTbIoTNotiBrd(HttpServletRequest request,  @RequestParam String jsonData) throws BizException {
		TbIoTNotiBrdDTO notiBoardDto;
		try {
			notiBoardDto = (TbIoTNotiBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTNotiBrdDTO.class);
			notiBoardSvc.updateTbIoTNotiBrd(request, notiBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteIotMultiNotiBoard", method = RequestMethod.POST)
	public ComResponseDto<?> deleteNotiBoard(HttpServletRequest request, @RequestBody TbIoTNotiBrdDTO notiBoardDto) throws BizException {
		boolean result = notiBoardSvc.deleteMultiNotiBoard(request, notiBoardDto);
		return responseComUtil.setResponse200ok(result);
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public HttpServletResponse downloadFile(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws UnsupportedEncodingException {
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = notiBoardSvc.selectFileName(tbIoTBrdFileListDTO);
		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());
		return response;
	}

}
