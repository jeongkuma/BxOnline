package kr.co.scp.common.iotSvc.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcCombinedDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcMDto;
import kr.co.scp.common.iotSvc.svc.TbIotSvcSvc;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*",  exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotsvc")
public class TbIotSvcCTL {

	@Autowired
	private TbIotSvcSvc tbIotSvcSvc;

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Value("${file.upload-dir-map}")
	private String FILE_UPLOAD_PATH;

	/**
	 * 서비스 목록 조회
	 * @param request
	 * @return ComResponseDto<List<TbIotTreeDTO>>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotSvc", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotSvc(HttpServletRequest request, @RequestBody TbIotSvcDto svcDto) throws BizException {
		List<TbIotTreeDTO> tbIotOrgDtoList = tbIotSvcSvc.retrieveIotSvc(svcDto);
		return responseComUtil.setResponse200ok(tbIotOrgDtoList);
	}

	/**
	 * 서비스 단일 조회
	 * @param request
	 * @param TbIotSvcDto
	 * @return TbIotSvcDto
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotSvcBySeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotSvcBySeq(HttpServletRequest request, @RequestBody TbIotSvcDto svcDto) throws BizException {
		HashMap<String, Object> returnMap = tbIotSvcSvc.retrieveIotSvcBySeq(svcDto);
		return responseComUtil.setResponse200ok(returnMap);
	}
	
	/**
	 * 서비스 등록
	 * @param request
	 * @param TbIotSvcDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @Author joseph 
	 */
	@RequestMapping(value = "/createIotSvc", method = RequestMethod.POST)
	public ComResponseDto<?> createIotSvc(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		try {
			TbIotSvcCombinedDto tbIotSvcDTO = (TbIotSvcCombinedDto) WebCommUtil.stringToObject(jsonData, TbIotSvcCombinedDto.class);
			tbIotSvcSvc.createIotSvc(request, tbIotSvcDTO);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException(e, "ObjectMapperError");
		} catch (IOException e) {			
			throw new BizException(e, "fileUploadError");
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 단말유형 조회
	 * @param request
	 * @param TbIotSvcMDto
	 * @return TbIotSvcMDto
	 * @throws BizException
	 * @Author toto8948
	 */
	@RequestMapping(value = "/retrieveIotDevClsList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDevClsList(HttpServletRequest request, @RequestBody TbIotSvcMDto tbIotSvcMDto) throws BizException {
		HashMap<String, Object> retrieveDevClsList = tbIotSvcSvc.retrieveDevClsList(tbIotSvcMDto);
		return responseComUtil.setResponse200ok(retrieveDevClsList);
	}
	

	/**
	 * 서비스별 단말 유형 조회
	 * @param request
	 * @param TbIotSvcMDto
	 * @return TbIotSvcMDto
	 * @throws BizException
	 * @Author toto8948
	 */
	@RequestMapping(value = "/getServiceDevCls", method = RequestMethod.POST)
	public ComResponseDto<?> getServiceDevCls(HttpServletRequest request, @RequestBody TbIotSvcMDto tbIotSvcMDto) throws BizException {
		HashMap<String, Object> retrieveDevClsList = tbIotSvcSvc.getServiceDevCls(tbIotSvcMDto);
		return responseComUtil.setResponse200ok(retrieveDevClsList);
	}

	/**
	 * 단말유형 등록
	 * @param request
	 * @param TbIotSvcDto
	 * @return TbIotSvcDto
	 * @throws BizException
	 * @Author toto8948
	 */
	@RequestMapping(value = "/createIotSvcDevMap", method = RequestMethod.POST)
	public ComResponseDto<?> createIotSvcDevMap(HttpServletRequest request, @RequestBody TbIotSvcMDto tbIotSvcMDto) throws BizException {
		tbIotSvcSvc.createIotSvcDevMap(tbIotSvcMDto);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 서비스 수정
	 * @param request
	 * @param TbIotSvcDto
	 * @return TbIotSvcDto
	 * @throws BizException
	 * @Author toto8948
	 */
	@RequestMapping(value = "/updateIotSvc", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotSvc(HttpServletRequest request, @RequestParam String jsonData) throws BizException {
		try {
			TbIotSvcCombinedDto tbIotSvcDTO = (TbIotSvcCombinedDto) WebCommUtil.stringToObject(jsonData, TbIotSvcCombinedDto.class);
			tbIotSvcSvc.updateIotSvc(request, tbIotSvcDTO);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException("ObjectMapperError");
		} catch (IOException e) {			
			throw new BizException("fileUploadError");
		} 
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 이미지 파일 다운로드
	 * @param request
	 * @param TbIoTBrdFileListDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/downloadImage", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public HttpServletResponse downloadImage(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileDTO) throws BizException{
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = tbIotSvcSvc.getFile(tbIoTBrdFileDTO);		
		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());		
		return response;
	}
	
	
	/**
	 * 서비스 공통 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return ComResponseDto<?> 
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotCmCdForSvc", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCdForSvc(HttpServletRequest request) throws BizException {
		List<HashMap<String, Object>> tbIotUseDTORn = tbIotSvcSvc.retrieveIotCmCdForSvc();
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
	
	/**
	 * 단말 유형 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return ComResponseDto<?> 
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotCmCdForDevDlsCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCdForDevDlsCd(HttpServletRequest request, @RequestBody TbIotSvcMDto tbIotSvcMDto) throws BizException {
		List<HashMap<String, Object>> tbIotUseDTORn = tbIotSvcSvc.retrieveIotCmCdForDevDlsCd(tbIotSvcMDto);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
	
}