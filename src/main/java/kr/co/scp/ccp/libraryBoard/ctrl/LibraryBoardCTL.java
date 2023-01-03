package kr.co.scp.ccp.libraryBoard.ctrl;

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

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResDetailDTO;
import kr.co.scp.ccp.libraryBoard.svc.LibraryBoardSVC;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/IotlibraryBoard")
public class LibraryBoardCTL {

	@Autowired
	private LibraryBoardSVC libarayBoardASvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Value("${file.upload-dir-lib}")
	private String FILE_UPLOAD_PATH;


	/**
	 * @Author 김희운
	 * @param request
	 * @param libraryBoardDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotLibraryBoardList", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveLibraryBoardList(HttpServletRequest request, @RequestBody TbIoTLibBrdDTO libraryBoardDto) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> retrieveLibraryBoardList = libarayBoardASvc.retrieveLibraryBoardList(libraryBoardDto);

		return responseComUtil.setResponse200ok(retrieveLibraryBoardList);
	}

	@RequestMapping(value = "/retrieveIotLibraryBoardDetail", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveLibraryBoardDetail(HttpServletRequest request, @RequestBody TbIoTLibBrdDTO libraryBoardDto) throws BizException {

		TbIoTLibBrdResDetailDTO tbIoTLibBrdResDetailDTO = libarayBoardASvc.retrieveLibraryBoardDetail(libraryBoardDto);

		return responseComUtil.setResponse200ok(tbIoTLibBrdResDetailDTO);
	}

	@RequestMapping(value = "/deleteIotLibraryBoard", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> deleteLibraryBoard(HttpServletRequest request, @RequestBody TbIoTLibBrdDTO libraryBoardDto) throws BizException {

		libarayBoardASvc.deleteLibraryBoard(libraryBoardDto);

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/createIotLibBoard", method = RequestMethod.POST, headers="X-APIVERSION=1")
	@ResponseBody
	public ComResponseDto<?> insertTbIoTLibBrdDTO(HttpServletRequest request, @RequestParam String jsonData ) throws BizException {
		TbIoTLibBrdDTO libraryBoardDto;
		try {
			libraryBoardDto = (TbIoTLibBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTLibBrdDTO.class);
			libarayBoardASvc.insertTbIoTLibBrdDTO(request,libraryBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/updateIotLibBoard", method = RequestMethod.POST, headers="X-APIVERSION=1")
	@ResponseBody
	public ComResponseDto<?> updateTbIoTLibBrdDTO(HttpServletRequest request,@RequestParam String jsonData) throws BizException {

		TbIoTLibBrdDTO libraryBoardDto;
		try {
			libraryBoardDto = (TbIoTLibBrdDTO) WebCommUtil.stringToObject(jsonData, TbIoTLibBrdDTO.class);
			libarayBoardASvc.updateTbIoTLibBrdDTO(request, libraryBoardDto);
		} catch (IOException e) {
			throw new BizException("fileUploadFail");
		}

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteIotMultiLibraryBoard", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> deleteMultiLibraryBoard(HttpServletRequest request, @RequestBody TbIoTLibBrdDTO libraryBoardDto) throws BizException {

		libarayBoardASvc.deleteMultiLibraryBoard(request, libraryBoardDto);

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public HttpServletResponse downloadFile(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileListDTO) {

		//fileName, orgFileName 가져오기
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = libarayBoardASvc.selectFileName(tbIoTBrdFileListDTO);

		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());
		return response;
	}












}
