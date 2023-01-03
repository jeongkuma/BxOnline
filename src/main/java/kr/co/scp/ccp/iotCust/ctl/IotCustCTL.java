package kr.co.scp.ccp.iotCust.ctl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustCombineDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustMDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustSDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotCust.svc.IotCustSVC;
import kr.co.scp.ccp.login.dto.LogoDTO;
import kr.co.scp.ccp.login.svc.LoginSVC;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins="*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotcust")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IotCustCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotCustSVC iotCustSVC;

	@Autowired
	private LoginSVC loginSVC;

	@Value("${file.upload-dir-cust}")
	private String FILE_UPLOAD_PATH;

	/**
	 * 고객사 조회
	 * @param request
	 * @param tbIotCustDto
	 * @return List<tbIotCustDto>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveIotCust", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCust(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = iotCustSVC.retrieveIotCust(tbIotCustDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 고객사 (드롭박스) 조회
	 * @param request
	 * @param tbIotCustDto
	 * @return List<tbIotCustDto>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveIotCustAll", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCustAll(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = iotCustSVC.retrieveIotCustAll(tbIotCustDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
		/**
         * 고객사 단일 조회
         * @param request
         * @param tbIotCustDto
         * @return List<tbIotCustDto>
         * @throws BizException
         * @author 정병수
         */
	@RequestMapping(value = "/retrieveIotCustBySeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCustBySeq(HttpServletRequest request, @RequestBody TbIotCustUDTO tbIotCustDTO) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = iotCustSVC.retrieveIotCustBySeq(tbIotCustDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 고객사 등록
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/createIotCust", method = RequestMethod.POST)
	public ComResponseDto<?> createIotCust(HttpServletRequest request, @RequestParam String jsonData) throws BizException {

		try {
			TbIotCustCombineDTO tbIotCustDTO = (TbIotCustCombineDTO) WebCommUtil.stringToObject(jsonData, TbIotCustCombineDTO.class);
			iotCustSVC.createIoTCust(tbIotCustDTO, request);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException("ObjectMapperError");
		} catch (IOException e) {
			throw new BizException("fileUploadError");
		}

		return responseComUtil.setResponse200ok();
	}

	/**
	 * 고객사 수정
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/updateIotCust", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotCust(HttpServletRequest request, @RequestParam String jsonData) throws BizException {

		try {
			TbIotCustDTO tbIotCustDTO = (TbIotCustDTO) WebCommUtil.stringToObject(jsonData, TbIotCustDTO.class);
			iotCustSVC.updateIotCust(request, tbIotCustDTO);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException("ObjectMapperError");
		} catch (IOException e) {
			throw new BizException("fileUploadError");
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 고객사 삭제
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/deleteIotCust", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotCust(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		iotCustSVC.deleteIotCust(tbIotCustDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 고객사 재가입
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/rejoinIotCust", method = RequestMethod.POST)
	public ComResponseDto<?> rejoinIotCust(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		iotCustSVC.deleteIotCust(tbIotCustDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 고객사 아이디 중복확인
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveDuplicatedCustId", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCustId(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		String result = iotCustSVC.retrieveDuplicatedCustId(tbIotCustDTO);
		TbIotCustMDTO returnObj = new TbIotCustMDTO();
		BigDecimal resultNumber = new BigDecimal(result);
		int chk = resultNumber.compareTo(new BigDecimal("0"));
		if(chk != 0) {
			throw new BizException("duplicatedCustId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}
		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 고객사명 중복확인
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveDuplicatedCustNm", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCustNm(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		String result = iotCustSVC.retrieveDuplicatedCustNm(tbIotCustDTO);
		BigDecimal resultNumber = new BigDecimal(result);
		int chk = resultNumber.compareTo(new BigDecimal("0"));
		TbIotCustMDTO returnObj = new TbIotCustMDTO();
		if(chk != 0) {
			throw new BizException("duplicatedCustNm");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}
		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 고객사 서비스 목록 조회
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveIotCustSvc", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCustSvc(HttpServletRequest request, @RequestBody TbIotCustDTO tbIotCustDTO) throws BizException {
		TbIotSvcDto returnDto = iotCustSVC.retrieveIotCustSvc();
		return responseComUtil.setResponse200ok(returnDto);
	}

	/**
	 * 로고 이미지 파일 다운로드
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/downloadLogo", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public HttpServletResponse downloadLogo(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileDTO) {
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = iotCustSVC.getFile(tbIoTBrdFileDTO);
		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();
		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());
		return response;
	}

	/**
	 * 고객사 서비스 목록 조회 for SelectBox
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author 정병수
	 */
	@RequestMapping(value = "/retrieveIotCustSelect", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCustSelect(HttpServletRequest request, @RequestBody TbIotCustSDTO tbIotCustSDTO ) throws BizException {
		List<TbIotCustSDTO> returnList = iotCustSVC.retrieveIotCustSelect(tbIotCustSDTO);
		return responseComUtil.setResponse200ok(returnList);
	}


	/**
	 * 고객사 로고 이미지 조회
	 * @param request
	 * @param logoDTO
	 * @return List<LogoDTO>
	 * @throws BizException
	 * @author jkuma
	 */
	@RequestMapping(value = "/retrieveIotLogo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotLogo(HttpServletRequest request, @RequestBody LogoDTO logoDTO) throws BizException {
		HashMap<String, Object> retrieveIotLogo = loginSVC.retrieveIotLogo(logoDTO);
		return responseComUtil.setResponse200ok(retrieveIotLogo);
	}
}
