package kr.co.scp.ccp.postBoard.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.WebCommUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResDetailDTO;
import kr.co.scp.ccp.postBoard.svc.PostBoardSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/IotPostBoard")

public class PostBoardCTL {

	@Autowired
	private PostBoardSVC postBoardSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Value("${file.upload-dir-post}")
	private String FILE_UPLOAD_PATH;


	/**
	 * @Author 김희운
	 * @param request
	 * @param postBoardDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotPostBoardList", method = RequestMethod.POST)
	public ComResponseDto<?> retrievePostBoardList(HttpServletRequest request, @RequestBody TbIoTPostBrdDTO postBoardDto) throws BizException {


		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> retrievePostBoardList = postBoardSvc.retrievePostBoardList(postBoardDto);

		return responseComUtil.setResponse200ok(retrievePostBoardList);
	}

	@RequestMapping(value = "/retrieveIotPostBoardDetail", method = RequestMethod.POST)
	public ComResponseDto<?> retrievePostBoardDetail(HttpServletRequest request, @RequestBody TbIoTPostBrdDTO postBoardDto) throws BizException {

		TbIoTPostBrdResDetailDTO tbIoTPostBrdResDetailDTO= postBoardSvc.retrievePostBoardDetail(postBoardDto);

		return responseComUtil.setResponse200ok(tbIoTPostBrdResDetailDTO);
	}

	@RequestMapping(value = "/deleteIotPostBoard", method = RequestMethod.POST)
	public ComResponseDto<?> deletePostBoard(HttpServletRequest request, @RequestBody TbIoTPostBrdDTO postBoardDto) throws BizException {

		postBoardSvc.deletePostBoard(postBoardDto);

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/createIotPostBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTPostBrdDTO(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		TbIoTPostBrdDTO postBoardDto;

		try {
			postBoardDto = (TbIoTPostBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTPostBrdDTO.class);
			postBoardSvc.insertTbIoTPostBrdDTO(request, postBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/updateIotPostBoard", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateTbIoTPostBrdDTO(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		TbIoTPostBrdDTO postBoardDto;
		try {
			postBoardDto = (TbIoTPostBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTPostBrdDTO.class);
			postBoardSvc.updateTbIoTPostBrdDTO(request, postBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}


		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteIotMultiPostBoard", method = RequestMethod.POST)
	public ComResponseDto<?> deleteMultiPostBoard(HttpServletRequest request, @RequestBody TbIoTPostBrdDTO postBoardDto) throws BizException {

		postBoardSvc.deleteMultiPostBoard(request, postBoardDto);

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public HttpServletResponse downloadFile(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileListDTO) {

		//fileName, orgFileName 가져오기
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = postBoardSvc.selectFileName(tbIoTBrdFileListDTO);

		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());
		return response;
	}


}
