package kr.co.scp.ccp.iotFaqBoard.ctrl;

import java.io.File;
import java.io.IOException;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDTO;
import kr.co.scp.ccp.iotFaqBoard.svc.FaqBoardSVC;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotfaqboard")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class FaqBoardCTL {

	@Autowired
	private FaqBoardSVC faqBoardSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Value("${file.upload-dir-faq}")
	private String FILE_UPLOAD_PATH;

	/**
	 * @Author jbs
	 * @param request
	 * @param faqBoardDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotFaqBoardList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotFaqBoardList(HttpServletRequest request, @RequestBody TbIoTFaqBrdDTO faqBoardDto) throws BizException {
		HashMap<String, Object> retrieveFaqBoardList = faqBoardSvc.retrieveIotFaqBoardList(faqBoardDto);
		return responseComUtil.setResponse200ok(retrieveFaqBoardList);
	}

	@RequestMapping(value = "/retrieveIotFaqBoardDetail", method = RequestMethod.POST)
	public ComResponseDto<?> getFaqBoardDetail(HttpServletRequest request, @RequestBody TbIoTFaqBrdDTO faqBoardDto) throws BizException {
		HashMap<String, Object> TbIoTFaqBrdDTO= faqBoardSvc.retrieveFaqBoardDetail(faqBoardDto);
		return responseComUtil.setResponse200ok(TbIoTFaqBrdDTO);
	}

	@RequestMapping(value = "/createIotFaqBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTFaqBrd(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		TbIoTFaqBrdDTO faqBoardDto;
//		ObjectMapper objectMapper = new ObjectMapper();
		try {
			faqBoardDto = (TbIoTFaqBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTFaqBrdDTO.class);
//			faqBoardDto = objectMapper.readValue(jsonData, TbIoTFaqBrdDTO.class);
			faqBoardSvc.insertTbIotFaqBrd(request, faqBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/updateIotFaqBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateIotFaqBrd(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		TbIoTFaqBrdDTO faqBoardDto;
//		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
		try {
			faqBoardDto = (TbIoTFaqBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTFaqBrdDTO.class);
//			faqBoardDto = objectMapper.readValue(jsonData, TbIoTFaqBrdDTO.class);
			faqBoardSvc.updateIotFaqBrd(request, faqBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteIotMultiFaqBoard", method = RequestMethod.POST)
	public ComResponseDto<?> deleteFaqBoard(HttpServletRequest request, @RequestBody TbIoTFaqBrdDTO faqBoardDto) throws BizException {
		boolean result = faqBoardSvc.deleteMultiFaqBoard(request, faqBoardDto);
		return responseComUtil.setResponse200ok(result);
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public HttpServletResponse downloadFile(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileListDTO) {
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = faqBoardSvc.selectFileName(tbIoTBrdFileListDTO);
		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());
		return response;
	}

	@RequestMapping(value = "/retrieveIotFaqBoardUser", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotFaqBoardUser(HttpServletRequest request, @RequestBody TbIoTFaqBrdDTO faqBoardDto) throws BizException {
		HashMap<String, Object> retrieveFaqBoardUser = faqBoardSvc.retrieveIotFaqBoardUser(faqBoardDto);
		return responseComUtil.setResponse200ok(retrieveFaqBoardUser);
	}

}
